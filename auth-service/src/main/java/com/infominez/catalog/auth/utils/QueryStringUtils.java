package com.infominez.catalog.auth.utils;

public interface QueryStringUtils {

    String GET_SYMTOM_DETAIL_BY_SYMTOM_ID_AND_LANGUAGE = "select * from symptom_content s  where s.language_id = :languageId and s.symptom_id=:symptomId";
    String FIND_SYMPTOMLANGUAGE_BY_LANGAUGE_ID = "select * from symptom_langauge s  where s.language_id = :languageId ";


}
