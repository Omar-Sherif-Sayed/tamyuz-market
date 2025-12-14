-- ============================================================================
-- CREATE SCHEMAS
-- ============================================================================

SELECT 'CREATE DATABASE tamyuz_market_db' WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'tamyuz_market_db'
);
-- CREATE DATABASE tamyuz_market_db;

-- ============================================================================
-- CREATE SCHEMAS
-- ============================================================================

-- If the schema doesn't exist, create it (idempotent)
CREATE SCHEMA IF NOT EXISTS tamyuz_market;

-- ============================================================================
-- CREATE USERS (Using IF NOT EXISTS for safety)
-- ============================================================================

-- Create user for user-management service
CREATE
USER tamyuz_market_user WITH PASSWORD 'password';

-- ============================================================================
-- GRANT PERMISSIONS
-- ============================================================================

-- 1. tamyuz_market
GRANT ALL
ON SCHEMA tamyuz_market TO tamyuz_market_user;
GRANT ALL PRIVILEGES ON ALL
TABLES IN SCHEMA tamyuz_market TO tamyuz_market_user;
GRANT ALL PRIVILEGES ON ALL
SEQUENCES IN SCHEMA tamyuz_market TO tamyuz_market_user;
GRANT ALL PRIVILEGES ON ALL
FUNCTIONS IN SCHEMA tamyuz_market TO tamyuz_market_user;

ALTER
DEFAULT PRIVILEGES IN SCHEMA tamyuz_market
    GRANT ALL ON TABLES TO tamyuz_market_user;
ALTER
DEFAULT PRIVILEGES IN SCHEMA tamyuz_market
    GRANT ALL ON SEQUENCES TO tamyuz_market_user;
ALTER
DEFAULT PRIVILEGES IN SCHEMA tamyuz_market
    GRANT ALL ON FUNCTIONS TO tamyuz_market_user;

ALTER
USER tamyuz_market_user SET search_path TO tamyuz_market, public;

-- OPTIONAL: Grant USAGE on public schema (if needed)
GRANT USAGE ON SCHEMA
public TO tamyuz_market_user;
