ALTER TABLE T_WORDS ADD COLUMN IF NOT EXISTS C_CREATED_AT TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT date();