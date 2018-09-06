var utils = {

    escapeHtml: function (unsafe) {
        return $('<div />').text(unsafe).html()
    },

    unescapeHtml: function (safe) {
        return $('<div />').html(safe).text();
    },
    
    getShortLocaleOfPage: function(){
        locale=this.getLocaleOfPage();
        if(locale.length>2){
            locale=locale.substr(0,2)
        }
        return locale;    
    },
    
    getLocaleOfPage: function(){
        var localeParameterName = "locale";
        var defaultLocale = "ru_RU";
        var locale = this.getURIParameter(localeParameterName);
        if(locale==null || this.allLocales.indexOf(locale)==-1){
            locale = defaultLocale;
        }
        return locale;
    },
    
    getURIParameter: function(name) {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
    },
    
    isValidDate: function (dateString){
        // First check for the pattern
        if(!/^\d{1,2}\.\d{1,2}\.\d{4}$/.test(dateString))
            return false;

        // Parse the date parts to integers
        var parts = dateString.split(".");
        var day = parseInt(parts[0], 10);
        var month = parseInt(parts[1], 10);
        var year = parseInt(parts[2], 10);

        // Check the ranges of month and year
        if(year < 1000 || year > 3000 || month == 0 || month > 12)
            return false;

        var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

        // Adjust for leap years
        if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
            monthLength[1] = 29;

        // Check the range of the day
        return day > 0 && day <= monthLength[month - 1];
    },
    
    allLocales: [
        "ms_MY",
        "ar_QA",
        "is_IS",
        "fi_FI",
        "pl",
        "en_MT",
        "it_CH",
        "nl_BE",
        "ar_SA",
        "ar_IQ",
        "es_PR",
        "es_CL",
        "fi",
        "de_AT",
        "da",
        "en_GB",
        "es_PA",
        "sr",
        "ar_YE",
        "mk_MK",
        "mk",
        "en_CA",
        "vi_VN",
        "nl_NL",
        "es_US",
        "zh_CN",
        "es_HN",
        "en_US",
        "fr",
        "th",
        "ar",
        "ar_MA",
        "lv",
        "de",
        "in_ID",
        "hr",
        "en_ZA",
        "ko_KR",
        "ar_TN",
        "in",
        "ja",
        "sr_RS",
        "be_BY",
        "zh_TW",
        "ar_SD",
        "pt",
        "is",
        "ja_JP",
        "es_BO",
        "ar_DZ",
        "ms",
        "es_AR",
        "ar_AE",
        "fr_CA",
        "sl",
        "es",
        "lt_LT",
        "sr_ME",
        "ar_SY",
        "ru_RU",
        "fr_BE",
        "es_ES",
        "bg",
        "iw_IL",
        "sv",
        "en",
        "iw",
        "da_DK",
        "es_CR",
        "zh_HK",
        "zh",
        "ca_ES",
        "th_TH",
        "uk_UA",
        "es_DO",
        "es_VE",
        "pl_PL",
        "ar_LY",
        "ar_JO",
        "it",
        "uk",
        "hu_HU",
        "ga",
        "es_GT",
        "es_PY",
        "bg_BG",
        "hr_HR",
        "sr_BA",
        "ro_RO",
        "fr_LU",
        "no",
        "lt",
        "en_SG",
        "es_EC",
        "sr_BA",
        "es_NI",
        "sk",
        "ru",
        "mt",
        "es_SV",
        "nl",
        "hi_IN",
        "et",
        "el_GR",
        "sl_SI",
        "it_IT",
        "ja_JP",
        "de_LU",
        "fr_CH",
        "mt_MT",
        "ar_BH",
        "sq",
        "vi",
        "sr_ME",
        "pt_BR",
        "no_NO",
        "el",
        "de_CH",
        "zh_SG",
        "ar_KW",
        "ar_EG",
        "ga_IE",
        "es_PE",
        "cs_CZ",
        "tr_TR",
        "cs",
        "es_UY",
        "en_IE",
        "en_IN",
        "ar_OM",
        "sr_CS",
        "ca",
        "be",
        "sr",	
        "ko",
        "sq_AL",
        "pt_PT",
        "lv_LV",
        "sr_RS",
        "sk_SK",
        "es_MX",
        "en_AU",
        "no_NO_NY",
        "en_NZ",
        "sv_SE",
        "ro",
        "ar_LB",
        "de_DE",
        "th_TH_TH",
        "tr",
        "es_CO",
        "en_PH",
        "et_EE",
        "el_CY",
        "hu",
        "fr_FR"
    ]
}

