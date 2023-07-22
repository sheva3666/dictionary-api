CREATE TABLE T_USER_SCORE (
    C_ID                UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    C_TENANT_ID         UUID NOT NULL,
    C_USER_EMAIL        TEXT NOT NULL,
    C_SCORE      INTEGER
    );