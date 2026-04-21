CREATE TABLE localization_strings (
                                      id INT PRIMARY KEY,
                                      `key` VARCHAR(255),
                                      value VARCHAR(255),
                                      language VARCHAR(10)
);

INSERT INTO localization_strings VALUES (1, 'language.label', 'Language', 'en');
INSERT INTO localization_strings VALUES (2, 'item.count.prompt', 'Number of items:', 'en');
INSERT INTO localization_strings VALUES (3, 'confirm.button', 'Confirm', 'en');
INSERT INTO localization_strings VALUES (4, 'calculate.button', 'Calculate', 'en');
INSERT INTO localization_strings VALUES (5, 'result.ready', 'Ready', 'en');
INSERT INTO localization_strings VALUES (6, 'item.prefix', 'Item {0}', 'en');
INSERT INTO localization_strings VALUES (7, 'cart.total', 'Total: {0}', 'en');