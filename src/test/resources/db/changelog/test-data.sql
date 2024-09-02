--liquibase formatted sql logicalFilePath:test-data.sql

--changeset mvillalobos:1
--comment: Simple test data for integration tests

INSERT INTO
    organizations (
        id, name, code, description, domain, identity_provider
    ) VALUES (
        DEFAULT, 'Galactic Empire', 'ge', 'The Galactic Empire', 'empire.com', 'https://identity.empire.com'
);

INSERT INTO
    organizational_units (
        id, organization_id, name, code, description
    ) VALUES (
        DEFAULT, 1, Death Star One Orbital Battle Station', 'ds1', 'First Death Star implementation.'
);

INSERT INTO
    users (
        id, organizational_unit_id, email_address, first_name,
        last_name, idp_id
) VALUES (
         DEFAULT, 1, 'palpetine@empire.org', 'Sheev',
        'Palpetine', '955f43d3-4dc6-48a2-ad53-eb01925e8da7'
);

INSERT INTO
    users (
        id, organizational_unit_id, email_address, first_name,
        last_name, idp_id
) VALUES (
         DEFAULT, 1, 'dmaul@empire.org', 'Darth',
        'Maul', 'http://'
);

INSERT INTO
    users (
    id, organizational_unit_id, email_address, first_name,
    last_name, idp_id
) VALUES (
     DEFAULT, 1, 'minmay@sdf1.org', 'Lynn',
     'Minmay', 'http://'
 );

INSERT INTO
    users (
    id, organizational_unit_id, email_address, first_name,
    last_name, idp_id
) VALUES (
     DEFAULT, 1, 'hichijo@mail.org', 'Hikaru',
     'Ichijo', 'http://'
 );



