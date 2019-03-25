databaseChangeLog = {

    changeSet(author: "aaron (generated)", id: "1553135716100-1") {
        createTable(tableName: "registration_code") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "registration_codePK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "token", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-2") {
        createTable(tableName: "release_item") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "end_time", type: "datetime")

            column(name: "release_section_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "start_time", type: "datetime")

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "BIGINT")

            column(name: "time_needed", type: "INT")
        }
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-3") {
        createTable(tableName: "release_package") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "start_time", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "completed_time", type: "datetime")
        }
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-4") {
        createTable(tableName: "release_parallel_items") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "is_complete", type: "BOOLEAN")

            column(name: "is_pre_release", type: "BOOLEAN")

            column(name: "order_num", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "is_post_release", type: "BOOLEAN")

            column(name: "release_package_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-5") {
        createTable(tableName: "role") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "rolePK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-6") {
        createTable(tableName: "user") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "userPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "enabled", type: "BOOLEAN") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-7") {
        createTable(tableName: "user_role") {
            column(name: "user_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "role_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-8") {
        addPrimaryKey(columnNames: "id", constraintName: "release_itemPK", tableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-9") {
        addPrimaryKey(columnNames: "id", constraintName: "release_packagePK", tableName: "release_package")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-10") {
        addPrimaryKey(columnNames: "id", constraintName: "release_parallel_itemsPK", tableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-11") {
        addPrimaryKey(columnNames: "user_id, role_id", constraintName: "user_rolePK", tableName: "user_role")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-12") {
        addUniqueConstraint(columnNames: "name", constraintName: "UC_RELEASE_PACKAGENAME_COL", tableName: "release_package")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-13") {
        addUniqueConstraint(columnNames: "authority", constraintName: "UC_ROLEAUTHORITY_COL", tableName: "role")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-14") {
        addUniqueConstraint(columnNames: "username", constraintName: "UC_USERUSERNAME_COL", tableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-15") {
        addUniqueConstraint(columnNames: "release_package_id, order_num", constraintName: "UK94a5ec520c43041f210053f83e0e", tableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-16") {
        addUniqueConstraint(columnNames: "release_section_id, name", constraintName: "UK9e12f2eda4d36a3621255b53d71b", tableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-17") {
        addForeignKeyConstraint(baseColumnNames: "release_package_id", baseTableName: "release_parallel_items", constraintName: "FK6rjm6l6d5ycam1pm4sacvsnv7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_package")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-18") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK859n2jvi8ivhui0rl0esws6o", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-19") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FKa68196081fvovjhkek5m97n3y", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-20") {
        addForeignKeyConstraint(baseColumnNames: "release_section_id", baseTableName: "release_item", constraintName: "FKcwjhkcafoy6v3f4b3pvvj92rr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553135716100-21") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "release_item", constraintName: "FKlugx2bdrr5teq0yhfvl7ke50i", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-1") {
        createTable(tableName: "release_items_parallels") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "end_time", type: "datetime")

            column(name: "release_item_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "release_section_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "start_time", type: "datetime")

            column(name: "user_id", type: "BIGINT")
        }
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-2") {
        createTable(tableName: "release_package_items") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "is_complete", type: "BIT")

            column(name: "is_pre_release", type: "BIT")

            column(name: "release_parallel_items_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "order_num", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "is_post_release", type: "BIT")

            column(name: "release_package_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-3") {
        createTable(tableName: "release_package_release_parallel_items") {
            column(name: "release_package_release_parallel_items_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "release_parallel_items_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-4") {
        createTable(tableName: "release_parallel_items_release_item") {
            column(name: "release_parallel_items_release_items_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "release_item_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-5") {
        addPrimaryKey(columnNames: "id", constraintName: "release_items_parallelsPK", tableName: "release_items_parallels")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-6") {
        addPrimaryKey(columnNames: "id", constraintName: "release_package_itemsPK", tableName: "release_package_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-7") {
        addUniqueConstraint(columnNames: "name", constraintName: "UC_RELEASE_ITEMNAME_COL", tableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-8") {
        addUniqueConstraint(columnNames: "release_package_id, order_num", constraintName: "UKcff3b3cc67be0e8eddb416b8e67d", tableName: "release_package_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-9") {
        addForeignKeyConstraint(baseColumnNames: "release_item_id", baseTableName: "release_parallel_items_release_item", constraintName: "FK3597xcuwtabv2vu3lhjwkphct", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-10") {
        addForeignKeyConstraint(baseColumnNames: "release_parallel_items_id", baseTableName: "release_package_release_parallel_items", constraintName: "FK6srirfdu3sovibk9k5df7x6ng", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-11") {
        addForeignKeyConstraint(baseColumnNames: "release_section_id", baseTableName: "release_items_parallels", constraintName: "FK9bngd9tehfgk4ypyrm62dgngs", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-12") {
        addForeignKeyConstraint(baseColumnNames: "release_package_release_parallel_items_id", baseTableName: "release_package_release_parallel_items", constraintName: "FK9iwjg256hnqo5bmlkhdenpr49", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_package")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-13") {
        addForeignKeyConstraint(baseColumnNames: "release_item_id", baseTableName: "release_items_parallels", constraintName: "FKbqgsul9wck3yt8icy16vnvs1t", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-14") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "release_items_parallels", constraintName: "FKcqt36iioi9rlpebg4ms5ib4rx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-15") {
        addForeignKeyConstraint(baseColumnNames: "release_parallel_items_release_items_id", baseTableName: "release_parallel_items_release_item", constraintName: "FKegek9bnvp0ug1vcqjnvwnbrg2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-16") {
        addForeignKeyConstraint(baseColumnNames: "release_package_id", baseTableName: "release_package_items", constraintName: "FKrjhav2kekvb788f932t8yspit", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_package")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-17") {
        addForeignKeyConstraint(baseColumnNames: "release_parallel_items_id", baseTableName: "release_package_items", constraintName: "FKskmnsr8mlky3j1mh2irnungr4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-18") {
        dropForeignKeyConstraint(baseTableName: "release_parallel_items", constraintName: "FK6rjm6l6d5ycam1pm4sacvsnv7")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-19") {
        dropForeignKeyConstraint(baseTableName: "release_item", constraintName: "FKcwjhkcafoy6v3f4b3pvvj92rr")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-20") {
        dropForeignKeyConstraint(baseTableName: "release_item", constraintName: "FKlugx2bdrr5teq0yhfvl7ke50i")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-21") {
        dropUniqueConstraint(constraintName: "UK94a5ec520c43041f210053f83e0e", tableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-22") {
        dropUniqueConstraint(constraintName: "UK9e12f2eda4d36a3621255b53d71b", tableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-23") {
        dropColumn(columnName: "end_time", tableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-24") {
        dropColumn(columnName: "is_complete", tableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-25") {
        dropColumn(columnName: "is_post_release", tableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-26") {
        dropColumn(columnName: "is_pre_release", tableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-27") {
        dropColumn(columnName: "order_num", tableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-28") {
        dropColumn(columnName: "release_package_id", tableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-29") {
        dropColumn(columnName: "release_section_id", tableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-30") {
        dropColumn(columnName: "start_time", tableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553225278926-31") {
        dropColumn(columnName: "user_id", tableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553225567566-1") {
        dropForeignKeyConstraint(baseTableName: "release_parallel_items_release_item", constraintName: "FK3597xcuwtabv2vu3lhjwkphct")
    }

    changeSet(author: "aaron (generated)", id: "1553225567566-2") {
        dropForeignKeyConstraint(baseTableName: "release_package_release_parallel_items", constraintName: "FK6srirfdu3sovibk9k5df7x6ng")
    }

    changeSet(author: "aaron (generated)", id: "1553225567566-3") {
        dropForeignKeyConstraint(baseTableName: "release_package_release_parallel_items", constraintName: "FK9iwjg256hnqo5bmlkhdenpr49")
    }

    changeSet(author: "aaron (generated)", id: "1553225567566-4") {
        dropForeignKeyConstraint(baseTableName: "release_parallel_items_release_item", constraintName: "FKegek9bnvp0ug1vcqjnvwnbrg2")
    }

    changeSet(author: "aaron (generated)", id: "1553225567566-5") {
        dropTable(tableName: "release_package_release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553225567566-6") {
        dropTable(tableName: "release_parallel_items_release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-1") {
        createTable(tableName: "release") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "end_time", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "start_time", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-2") {
        addColumn(tableName: "release_package_items") {
            column(name: "end_time", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-3") {
        addColumn(tableName: "release_package") {
            column(name: "is_complete", type: "bit") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-4") {
        addColumn(tableName: "release_package") {
            column(name: "release_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-5") {
        addColumn(tableName: "release_package_items") {
            column(name: "release_item_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-6") {
        addColumn(tableName: "release_package_items") {
            column(name: "start_time", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-7") {
        addColumn(tableName: "release_package_items") {
            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-8") {
        addPrimaryKey(columnNames: "id", constraintName: "releasePK", tableName: "release")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-9") {
        addUniqueConstraint(columnNames: "name", constraintName: "UC_RELEASENAME_COL", tableName: "release")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-10") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "release_package_items", constraintName: "FK2vlcvp9jgmc5a8lgfsbn509co", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-11") {
        addForeignKeyConstraint(baseColumnNames: "release_item_id", baseTableName: "release_package_items", constraintName: "FKbeuj1ys9dlmm0263qj6gtj0s4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-12") {
        addForeignKeyConstraint(baseColumnNames: "release_id", baseTableName: "release_package", constraintName: "FKhc4gsj3y2yxsj5tcvxaumvo78", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-13") {
        dropForeignKeyConstraint(baseTableName: "release_items_parallels", constraintName: "FK9bngd9tehfgk4ypyrm62dgngs")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-14") {
        dropForeignKeyConstraint(baseTableName: "release_items_parallels", constraintName: "FKbqgsul9wck3yt8icy16vnvs1t")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-15") {
        dropForeignKeyConstraint(baseTableName: "release_items_parallels", constraintName: "FKcqt36iioi9rlpebg4ms5ib4rx")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-16") {
        dropForeignKeyConstraint(baseTableName: "release_package_items", constraintName: "FKskmnsr8mlky3j1mh2irnungr4")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-17") {
        dropTable(tableName: "release_items_parallels")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-18") {
        dropTable(tableName: "release_parallel_items")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-19") {
        dropColumn(columnName: "release_parallel_items_id", tableName: "release_package_items")
    }

    changeSet(author: "aaron (generated)", id: "1553394168515-20") {
        dropColumn(columnName: "time_needed", tableName: "release_item")
    }

    changeSet(author: "aaron (generated)", id: "1553395975475-1") {
        dropNotNullConstraint(columnDataType: "datetime", columnName: "end_time", tableName: "release")
    }

    changeSet(author: "aaron (generated)", id: "1553395975475-2") {
        dropNotNullConstraint(columnDataType: "datetime", columnName: "start_time", tableName: "release")
    }

    changeSet(author: "aaron (generated)", id: "1553396531342-1") {
        createTable(tableName: "release_name") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "end_time", type: "datetime")

            column(name: "start_time", type: "datetime")

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553396531342-2") {
        addColumn(tableName: "release_package") {
            column(name: "release_name_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553396531342-3") {
        addPrimaryKey(columnNames: "id", constraintName: "release_namePK", tableName: "release_name")
    }

    changeSet(author: "aaron (generated)", id: "1553396531342-4") {
        addUniqueConstraint(columnNames: "name", constraintName: "UC_RELEASE_NAMENAME_COL", tableName: "release_name")
    }

    changeSet(author: "aaron (generated)", id: "1553396531342-5") {
        addForeignKeyConstraint(baseColumnNames: "release_name_id", baseTableName: "release_package", constraintName: "FKdp6s9uu3vnx42toenqjsqqiy3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "release_name")
    }

    changeSet(author: "aaron (generated)", id: "1553396531342-6") {
        dropForeignKeyConstraint(baseTableName: "release_package", constraintName: "FKhc4gsj3y2yxsj5tcvxaumvo78")
    }

    changeSet(author: "aaron (generated)", id: "1553396531342-7") {
        dropUniqueConstraint(constraintName: "UC_RELEASENAME_COL", tableName: "release")
    }

    changeSet(author: "aaron (generated)", id: "1553396531342-8") {
        dropTable(tableName: "release")
    }

    changeSet(author: "aaron (generated)", id: "1553396531342-9") {
        dropColumn(columnName: "release_id", tableName: "release_package")
    }

    changeSet(author: "aaron (generated)", id: "1553396531342-10") {
        dropNotNullConstraint(columnDataType: "datetime", columnName: "start_time", tableName: "release_package")
    }

    changeSet(author: "aaron (generated)", id: "1553397028986-2") {
        dropUniqueConstraint(constraintName: "UC_RELEASE_PACKAGENAME_COL", tableName: "release_package")
    }

    changeSet(author: "aaron (generated)", id: "1553397460944-1") {
        addColumn(tableName: "release_package") {
            column(name: "is_post_release", type: "bit")
        }
    }

    changeSet(author: "aaron (generated)", id: "1553397460944-2") {
        addColumn(tableName: "release_package") {
            column(name: "is_pre_release", type: "bit")
        }
    }

    changeSet(author: "aaron (generated)", id: "1553397460944-3") {
        addColumn(tableName: "release_package_items") {
            column(name: "time_needed", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553397460944-4") {
        addUniqueConstraint(columnNames: "release_name_id, name", constraintName: "UKebdea561d783de479f76e39d063f", tableName: "release_package")
    }


    changeSet(author: "aaron (generated)", id: "1553397460944-6") {
        dropColumn(columnName: "is_post_release", tableName: "release_package_items")
    }

    changeSet(author: "aaron (generated)", id: "1553397460944-7") {
        dropColumn(columnName: "is_pre_release", tableName: "release_package_items")
    }

    changeSet(author: "aaron (generated)", id: "1553397460944-8") {
        dropNotNullConstraint(columnDataType: "bigint", columnName: "user_id", tableName: "release_package_items")
    }

    changeSet(author: "aaron (generated)", id: "1553398211516-1") {
        addColumn(tableName: "release_package") {
            column(name: "order_number", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1553432423208-1") {
        dropNotNullConstraint(columnDataType: "datetime", columnName: "end_time", tableName: "release_package_items")
    }

    changeSet(author: "aaron (generated)", id: "1553432423208-2") {
        dropNotNullConstraint(columnDataType: "datetime", columnName: "start_time", tableName: "release_package_items")
    }

    changeSet(author: "aaron (generated)", id: "1553435702496-1") {
        addColumn(tableName: "release_package_items") {
            column(name: "completed_user_id", type: "bigint")
        }
    }

    changeSet(author: "aaron (generated)", id: "1553435702496-2") {
        addForeignKeyConstraint(baseColumnNames: "completed_user_id", baseTableName: "release_package_items", constraintName: "FKlsfgp41cvc7mkgayoemsaiui5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1553439521305-1") {
        addColumn(tableName: "release_name") {
            column(name: "planned_start_time", type: "datetime")
        }
    }
}
