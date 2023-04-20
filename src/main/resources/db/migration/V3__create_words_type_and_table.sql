CREATE TABLE T_WORDS (
    C_ID                    UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    C_TENANT_ID             UUID NOT NULL,
    C_USER                  TEXT NOT NULL,
    C_WORD                  TEXT NOT NULL,
    C_TRANSLATE             TEXT NOT NULL,
    C_TRANSLATE_LANGUAGE    TEXT NOT NULL,
    C_LANGUAGE              TEXT NOT NULL
    );