package com.ray.pominowner.global.config.category;

public enum InitialCategoryInfo {
    한식,
    분식,
    카페_디저트,
    돈까스_회_일식,
    치킨,
    피자,
    아시안_양식,
    중국집,
    족발_보쌈,
    야식,
    찜_탕,
    도시락,
    패스트푸드,
    프랜차이즈,
    ;

    private static final String IMAGE_PREFIX = "src/main/resources/categoryimage/";
    private static final String SUFFIX = ".png";

    public String url() {
        return IMAGE_PREFIX + this.name() + SUFFIX;
    }

}
