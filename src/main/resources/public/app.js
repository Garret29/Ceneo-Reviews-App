const app = angular.module('ceneoReviewApp', []);

app.service('transformationService', function () {
    this.transform = function (html, htmlDOMs) {
        let htmlDOMMain = $($.parseHTML(html));

        let product = {};
        product.reviews = [];

        product.model = htmlDOMMain.find("h1.product-name").text();
        product.additionalInfo = htmlDOMMain.find("div.ProductSublineTags").text();
        product.brand = htmlDOMMain.find("a[data-Brand]").attr("data-Brand");
        product.type = htmlDOMMain.find("a[data-GACategoryName]").attr("data-GACategoryName");

        let anonymousCount = 1;

        htmlDOMs.forEach(function (htmlDOM) {
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
                temp = $(obj).find(".reviewer-name-line").text();
                if(temp.includes("Użytkownik Ceneo")){
                    review.author = "Anonim"+anonymousCount++;
                } else {
                    review.author = temp
                }

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
        });
        return product;
    };
});

app.controller('controller', function ($scope, $http, transformationService, $location) {
        $scope.url = $location.absUrl();
        $scope.code = "";
        $scope.extracted = false;
        $scope.transformed = false;
        $scope.loaded = true;
        $scope.html = "";
        $scope.productsRecordsNumber = null;
        $scope.reviewsRecordsNumber = null;
        $scope.product = {};
        $scope.products = [];
        $scope.empty = true;
        $scope.htmlDOMs = [];
        $scope.currentReviewsNumber = 0;
        $scope.requestBody = {};


        $scope.ETL = function () {
            $scope.htmlDOMs = [];
            $scope.loaded = false;

            $.get("https://www.ceneo.pl/" + $scope.code, function (data) {
                $scope.html = data;
                let htmlDOM = $($.parseHTML($scope.html));
                let reviewCount = htmlDOM.find("span[itemprop='reviewCount']").text();
                if(reviewCount===""){
                    reviewCount=1;
                }

                $scope.currentReviewsNumber = Math.ceil(reviewCount / 10);
                let j = 0;

                for (let i = 1; i <= $scope.currentReviewsNumber; i++) {
                    $.get("https://www.ceneo.pl/" + $scope.code + "/opinie-" + i, function (data) {
                        $scope.htmlDOMs.push($($.parseHTML(data)));
                        j++;
                        if (j === $scope.currentReviewsNumber) {
                            let promise = new Promise(function (resolve, reject) {
                                $scope.transform();
                                resolve();
                            });
                            promise.then(function () {
                                $scope.load();
                            });
                        }
                    });
                }

            }).fail(function () {
                $scope.loaded = true;
                $scope.extracted = false;
                $scope.transformed = false;
                $scope.$apply();
            });
        };


        $scope.extract = function () {
            $scope.htmlDOMs = [];
            $scope.loaded = false;

            $.get("https://www.ceneo.pl/" + $scope.code, function (data) {
                $scope.html = data;
                let htmlDOM = $($.parseHTML($scope.html));
                let reviewCount = htmlDOM.find("span[itemprop='reviewCount']").text();

                if(reviewCount===""){
                    reviewCount=1;
                }

                $scope.currentReviewsNumber = Math.ceil(reviewCount / 10);

                let promise = new Promise(function (resolve) {
                    let array = [];
                    for (let j = 1; j <= $scope.currentReviewsNumber; j++) {

                        $.get("https://www.ceneo.pl/" + $scope.code + "/opinie-" + j, function (data) {
                            $scope.htmlDOMs.push($($.parseHTML(data)));
                            array.push(j);
                            console.log(array.length + "_PAGE loaded");
                            if (array.length === $scope.currentReviewsNumber) {
                                resolve();
                            }
                        });
                    }
                });
                promise.then(function () {
                    console.log("DONE LOADING");
                    $scope.extracted=true;
                    $scope.$apply();
                })
            }).fail(function () {
                $scope.loaded = true;
                $scope.extracted = false;
                $scope.transformed = false;
                $scope.$apply();
            });
        };


        $scope.transform = function () {
            $scope.extracted = false;
            $scope.product = transformationService.transform($scope.html, $scope.htmlDOMs);
            $scope.product.productid = $scope.code;

            if (!$scope.products.filter(function (elem) {
                    return elem.model === $scope.product.model
                }).length > 0) {
                $scope.products.push($scope.product)
            }
            $scope.requestBody = $scope.product;
            $scope.transformed = true;
        };

        $scope.load = function () {
            $scope.transformed = false;

            $http({
                url: $scope.url + "products",
                dataType: "json",
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                data: JSON.stringify($scope.requestBody)
            }).then(function (response) {
                $scope.productsRecordsNumber = response.data["newproducts"];
                $scope.reviewsRecordsNumber = response.data["newreviews"];
            });

            $scope.loaded = true;
        };

        $scope.appInit = function () {
            /*
            $.ajaxPrefilter(function (options) {
                if (options.crossDomain && jQuery.support.cors) {
                    const http = (window.location.protocol === 'http:' ? 'http:' : 'https:');
                    options.url = http + '//cors-anywhere.herokuapp.com/' + options.url;
                }
            });
            */
        };

        $scope.deleteProduct = function (prod) {
            $http({
                url: $scope.url + "products/" + prod.productid + "/delete",
                method: "POST"
            }).then(function (response) {
                $scope.products = $scope.products.filter(function (item) {
                    return item !== prod
                });

                $scope.product=$scope.products[0];
                $scope.productsRecordsNumber = response.data["newproducts"];
                $scope.reviewsRecordsNumber = response.data["newreviews"];
            });
        };

        $scope.deleteAll = function () {
            $http({
                url: $scope.url + "products/delete-all",
                method: "POST"
            }).then(function (response) {
                $scope.products = [];
                $scope.product = {};
                $scope.productsRecordsNumber = response.data["newproducts"];
                $scope.reviewsRecordsNumber = response.data["newreviews"];
            });
        };

        $scope.deleteReviews = function (prod) {
            $http({
                url: $scope.url + "products/" + prod.productid + "/reviews/delete",
                method: "POST"
            }).then(function (response) {
                $scope.productsRecordsNumber = response.data["newproducts"];
                $scope.reviewsRecordsNumber = response.data["newreviews"];
            });
        };

        $scope.assignProduct = function (product) {
            if ($scope.loaded === true) {
                $scope.product = product;
            }
        };

        $scope.reset = function () {
            $scope.url = $location.absUrl();
            $scope.code = "";
            $scope.extracted = false;
            $scope.transformed = false;
            $scope.loaded = true;
            $scope.html = "";
            $scope.productsRecordsNumber = null;
            $scope.reviewsRecordsNumber = null;
            $scope.product = {};
            $scope.products = [];
            $scope.empty = true;
            $scope.htmlDOMs = [];
            $scope.currentReviewsNumber = 0;
            $scope.requestBody = {};
        }
    }
);