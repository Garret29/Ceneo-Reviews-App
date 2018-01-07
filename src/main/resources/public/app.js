const app = angular.module('ceneoReviewApp', []);

app.service('transformationService', function () {
    this.transform = function (html) {
        let htmlDOM = $($.parseHTML(html));

        let product = {};
        product.reviews = [];

        product.model = htmlDOM.find("h1.product-name").text();
        product.additionalInfo = htmlDOM.find("div.ProductSublineTags").text();
        product.brand = htmlDOM.find("a[data-Brand]").attr("data-Brand");
        product.type = htmlDOM.find("a[data-GACategoryName]").attr("data-GACategoryName");
        // console.log(product.type);
        // console.log(product.additionalInfo);
        // console.log(product.brand);
        // console.log(product.model);

        let reviewsDOM = htmlDOM.find("li.review-box.js_product-review").not(".product-answer");
        // console.log(reviewsDOM.length);

        reviewsDOM.each(function (i, obj) {
            let review = {};
            review.pros = [];
            review.cons = [];
            review.recommendation = $(obj).find(".product-review-summary em").text();
            let temp = $(obj).find(".review-score-count").text();
            temp = temp.substring(0, temp.indexOf("/"));

            if (temp.indexOf(",") > -1) {
                temp = temp.substring(0, temp.indexOf(",")) + "." + temp.substring(temp.indexOf(",") + 1);
            }

            review.rating = temp;
            review.author = $(obj).find(".reviewer-name-line").text();
            review.summary = $(obj).find(".product-review-body").text();
            review.upvotes = $(obj).find(".vote-yes").attr("data-total-vote");
            review.downvotes = $(obj).find(".vote-no").attr("data-total-vote");
            let prosDOM = $(obj).find(".pros-cell ul li");
            prosDOM.each(function (i, obj) {
                review.pros.push($(obj).text());
                // console.log($(obj).text());
            });

            let consDOM = $(obj).find(".cons-cell ul li");
            consDOM.each(function (i, obj) {
                review.cons.push($(obj).text());
                // console.log($(obj).text());
            });

            console.log(review.recommendation);
            // console.log(review.rating);
            // console.log(review.author);
            // console.log(review.summary);
            // console.log(review.upvotes);
            // console.log(review.downvotes);

            product.reviews.push(review);
        });

        return product;
    };
});

app.controller('controller', function ($scope, $http, transformationService, $location) {
        $scope.url = $location.absUrl();
        $scope.code = "47667343";
        $scope.extracted = false;
        $scope.transformed = false;
        $scope.loaded = true;
        $scope.html = "";

        $scope.product = {
            "additionalInfo": "",
            "brand": "",
            "model": "",
            "type": "",
            "reviews": []
        };

        $scope.review = {
            "pros": [],
            "cons": [],
            "author": "",
            "rating": "",
            "summary": "",
            "recommendation": "",
            "upvotes": 0,
            "downvotes": 0,
            "product": null
        };

        $scope.ETL = function () {
            $.ajaxPrefilter(function (options) {
                if (options.crossDomain && jQuery.support.cors) {
                    const http = (window.location.protocol === 'http:' ? 'http:' : 'https:');
                    options.url = http + '//cors-anywhere.herokuapp.com/' + options.url;
                }
            });
            $scope.loaded = false;
            $.get("https://www.ceneo.pl/" + $scope.code, function (data) {
                $scope.html = data;
                $scope.$apply()
            }).then(function () {

                let htmlDOM = $($.parseHTML($scope.html));

                let product = {};
                product.reviews = [];

                product.model = htmlDOM.find("h1.product-name").text();
                product.additionalInfo = htmlDOM.find("div.ProductSublineTags").text();
                product.brand = htmlDOM.find("a[data-Brand]").attr("data-Brand");
                product.type = htmlDOM.find("a[data-GACategoryName]").attr("data-GACategoryName");

                let reviewsDOM = htmlDOM.find("li.review-box.js_product-review").not(".product-answer");

                reviewsDOM.each(function (i, obj) {
                    let review = {};
                    review.pros = [];
                    review.cons = [];
                    review.recommendation = $(obj).find(".product-review-summary em").text();
                    let temp = $(obj).find(".review-score-count").text();
                    temp = temp.substring(0, temp.indexOf("/"));

                    if (temp.indexOf(",") > -1) {
                        temp = temp.substring(0, temp.indexOf(",")) + "." + temp.substring(temp.indexOf(",") + 1);
                    }

                    review.rating = temp;
                    review.author = $(obj).find(".reviewer-name-line").text();
                    review.summary = $(obj).find(".product-review-body").text();
                    review.upvotes = $(obj).find(".vote-yes").attr("data-total-vote");
                    review.downvotes = $(obj).find(".vote-no").attr("data-total-vote");
                    let prosDOM = $(obj).find(".pros-cell ul li");
                    prosDOM.each(function (i, obj) {
                        review.pros.push($(obj).text());
                    });

                    let consDOM = $(obj).find(".cons-cell ul li");
                    consDOM.each(function (i, obj) {
                        review.cons.push($(obj).text());
                    });
                    product.reviews.push(review);
                });


                $scope.product = product;

                $http({
                    url: $scope.url + "/reviews",
                    dataType: "json",
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    data: JSON.stringify($scope.product)
                }).then(function (response) {

                });

                $scope.loaded = true;
            });
        };




        $scope.extract = function () {

            $.ajaxPrefilter(function (options) {
                if (options.crossDomain && jQuery.support.cors) {
                    const http = (window.location.protocol === 'http:' ? 'http:' : 'https:');
                    options.url = http + '//cors-anywhere.herokuapp.com/' + options.url;
                }
            });

            $scope.loaded = false;
            $.get("https://www.ceneo.pl/" + $scope.code, function (data) {
                $scope.html = data;
                $scope.extracted = true;
                $scope.$apply()
            });
        };

        $scope.transform = function () {
            $scope.extracted = false;
            $scope.product = transformationService.transform($scope.html);
            $scope.transformed = true;
        };

        $scope.load = function () {
            $scope.transformed = false;

            $http({
                url: $scope.url + "/reviews",
                dataType: "json",
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                data: JSON.stringify($scope.product)
            }).then(function (response) {

            });

            $scope.loaded = true;
        }
    }
)
;