package com.github.boneill42.cql;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnList;

public class ChildCqlDaoTest {
    private static Logger LOG = LoggerFactory.getLogger(ChildCqlDaoTest.class);

    @Test
    public void testAddToy() throws Exception {
        ChildCqlDao dao = new ChildCqlDao("localhost");
        dao.addToy("owen.oneill", "chromebook");
    }

    public void log(ColumnList<String> columns) {
        for (Column<String> column : columns) {
            LOG.debug("[" + column.getName() + "]->[" + column.getStringValue() + "]");
        }
    }
}
