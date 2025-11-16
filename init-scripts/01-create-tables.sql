    -- =====================================================
    -- DDL Base de Datos - PostgreSQL
    -- Basado en el diagrama de entidades proporcionado
    -- =====================================================

    -- Crear esquema si no existe
    CREATE SCHEMA IF NOT EXISTS proyecto_db;

    -- Configurar búsqueda de esquemas
    SET search_path TO proyecto_db, public;

    -- =====================================================
    -- TABLAS MAESTRAS / CATÁLOGOS
    -- =====================================================

    -- Tabla: COUNTRY
    CREATE TABLE country (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        code VARCHAR(10) UNIQUE NOT NULL
    );

    -- Tabla: STATE_DEPARTMENT
    CREATE TABLE state_department (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        code VARCHAR(10) NOT NULL,
        country_id INTEGER NOT NULL,
        FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE RESTRICT
    );

    -- Tabla: CITY
    CREATE TABLE city (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        code VARCHAR(10),
        state_department_id INTEGER NOT NULL,
        FOREIGN KEY (state_department_id) REFERENCES state_department(id) ON DELETE RESTRICT
    );

    CREATE TABLE street_type (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL
    );


    -- =====================================================
    -- GESTIÓN DE ADDRESSES
    -- =====================================================

    -- Tabla: ADDRESS
    CREATE TABLE address (
        id SERIAL PRIMARY KEY,
        street_type_id INTEGER NOT NULL,
        main_number INTEGER,
        secondary_number INTEGER,
        property_number INTEGER,
        complement VARCHAR(255),
        neighborhood VARCHAR(255),
        postal_code VARCHAR(20),
        city_id INTEGER NOT NULL,
        state_department_id INTEGER NOT NULL,
        country_id INTEGER NOT NULL,
        coordinates POINT,
        created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (street_type_id) REFERENCES street_type(id) ON DELETE RESTRICT,
        FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE RESTRICT,
        FOREIGN KEY (state_department_id) REFERENCES state_department(id) ON DELETE RESTRICT,
        FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE RESTRICT
    );


    -- =====================================================
    -- GESTIÓN DE USUARIOS
    -- =====================================================

    -- Tabla: USER
    CREATE TABLE "user" (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        username VARCHAR(50) NOT NULL UNIQUE,
        email VARCHAR(100) NOT NULL UNIQUE,
        password_hash VARCHAR(255) NOT NULL,
        first_name VARCHAR(50) NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        address_id INTEGER NOT NULL,
        phone VARCHAR(20),
        created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        last_login TIMESTAMPTZ,
        FOREIGN KEY (address_id) REFERENCES "address"(id) ON DELETE RESTRICT
    );


    CREATE TABLE restaurant (
        id SERIAL PRIMARY KEY,
        nit VARCHAR(20) NOT NULL,
        dv CHAR(1) NOT NULL, -- Dígito de verificación
        address_id INTEGER NOT NULL,
        legal_name VARCHAR(255) NOT NULL, -- Razón social
        comercial_name VARCHAR(255), -- Nombre comercial
        person_type VARCHAR(50) NOT NULL, -- Natural / Jurídica
        tax_regime VARCHAR(100) NOT NULL, -- Régimen tributario
        tax_responsibilities VARCHAR(255), -- Responsabilidades fiscales
        dian_resolution_id VARCHAR(50) NOT NULL, -- Número de resolución DIAN
        dian_resolution_expedition DATE NOT NULL, -- Fecha de expedición
        dian_resolution_expiration DATE NOT NULL, -- Fecha de expiración
        dian_billing_range_start INT NOT NULL, -- Rango inicial
        dian_billing_range_end INT NOT NULL, -- Rango final
        dian_billing_prefix VARCHAR(10), -- Prefijo de la resolución
        dian_email VARCHAR(255) NOT NULL, -- Correo electrónico registrado en DIAN
        order_day_counter INT DEFAULT 0,
        FOREIGN KEY (address_id) REFERENCES "address"(id) ON DELETE RESTRICT
    );

    CREATE TABLE zone (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        status VARCHAR(50) DEFAULT 'AVAILABLE',
        color VARCHAR(20),
        description VARCHAR(512),
        restaurant_id INTEGER NOT NULL,
        created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE
    );

    CREATE TABLE user_restaurant (
        id SERIAL PRIMARY KEY,
        role VARCHAR(50) NOT NULL,
        is_active BOOLEAN DEFAULT TRUE,
        created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        restaurant_id INTEGER NOT NULL,
        user_id UUID NOT NULL,
        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE,
        FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
    );

    -- =====================================================
    -- GESTIÓN DE ÓRDENES Y COMISIONES
    -- =====================================================
    -- Tabla: order consumable detail

    -- Tabla: TABLE (debe crearse primero porque ORDER la referencia)
    CREATE TABLE "table" (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        capacity INTEGER NOT NULL,
        status VARCHAR(50) DEFAULT 'AVAILABLE',
        zone_id INTEGER NOT NULL,
        restaurant_id INTEGER NOT NULL,
        current_order_id INTEGER,
        occupied_since TIMESTAMPTZ,
        responsable_id UUID,
        created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (zone_id) REFERENCES zone(id) ON DELETE CASCADE,
        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE,
        FOREIGN KEY (responsable_id) REFERENCES "user"(id) ON DELETE RESTRICT
    );

    -- Tabla: ORDER
    CREATE TABLE "order" (
        id SERIAL PRIMARY KEY,
        order_number INTEGER NOT NULL,
        restaurant_id INTEGER NOT NULL,
        table_id INTEGER NOT NULL,
        seller_id UUID NOT NULL,
        client_id UUID,
        status VARCHAR(30) NOT NULL,
        total_amount DECIMAL(13,2) NOT NULL,
        currency VARCHAR(3),
        payment_method VARCHAR(30),
        shipping_address_id INTEGER,
        billing_address_id INTEGER,
        notes VARCHAR(512),
        canceled_reason VARCHAR(256),
        created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        iva DECIMAL(5,2),
        inc DECIMAL(5,2),
        FOREIGN KEY (seller_id) REFERENCES "user"(id) ON DELETE RESTRICT,
        FOREIGN KEY (client_id) REFERENCES "user"(id) ON DELETE RESTRICT,
        FOREIGN KEY (shipping_address_id) REFERENCES address(id) ON DELETE RESTRICT,
        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE RESTRICT,
        FOREIGN KEY (billing_address_id) REFERENCES address(id) ON DELETE RESTRICT,
        FOREIGN KEY (table_id) REFERENCES "table"(id) ON DELETE RESTRICT
    );

    -- Agregar la foreign key de table.current_order_id después de crear ORDER
    ALTER TABLE "table"
    ADD CONSTRAINT fk_table_current_order
    FOREIGN KEY (current_order_id) REFERENCES "order"(id) ON DELETE RESTRICT;


    CREATE TABLE consumable_category (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        description TEXT,
        restaurant_id INTEGER NOT NULL,
        created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
    );


    CREATE TABLE consumable (
        id SERIAL PRIMARY KEY,
        name VARCHAR(200) NOT NULL,
        description TEXT,
        sku VARCHAR(50) UNIQUE,
        image_url TEXT,
        category_id INTEGER NOT NULL,
        restaurant_id INTEGER NOT NULL,
        unit_price DECIMAL(10,2) NOT NULL,
        currency VARCHAR(3) DEFAULT 'COP',
        stock_quantity INTEGER DEFAULT NULL,
        min_stock_level INTEGER DEFAULT NULL,
        is_active BOOLEAN DEFAULT TRUE,
        time_slots JSONB  NOT NULL,
        ingredients JSONB,
        nutritional_info JSONB,
        created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (category_id) REFERENCES consumable_category(id) ON DELETE CASCADE,
        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE
    );

    -- Tabla: ORDER_CONSUMABLE
    CREATE TABLE order_consumable (
        id SERIAL PRIMARY KEY,
        order_id INTEGER NOT NULL,
        consumable_id INTEGER NOT NULL,
        quantity INTEGER NOT NULL DEFAULT 1,
        unit_price DECIMAL(10,2) NOT NULL,
        total_price DECIMAL(12,2) NOT NULL,
        created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (order_id) REFERENCES "order"(id) ON DELETE RESTRICT,
        FOREIGN KEY (consumable_id) REFERENCES consumable(id) ON DELETE RESTRICT
    );