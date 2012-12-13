package com.github.boneill42;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnList;

public class ListEntryDaoTest {
    private static Logger LOG = LoggerFactory.getLogger(ListEntryDaoTest.class);

    @Test
    public void testWrite() throws Exception {
        ListEntryDao dao = new ListEntryDao("localhost:9160", AstyanaxDao.KEYSPACE);
        ListEntry listEntry = new ListEntry();
        listEntry.state = "PA";
        listEntry.zip = "19464";
        listEntry.childId = "hank";
        dao.write("nice:USA", listEntry);
    }

    @Test
    public void testRead() throws Exception {
        ListEntryDao dao = new ListEntryDao("localhost:9160", AstyanaxDao.KEYSPACE);
        log(dao.read("nice:USA"));
    }

    @Test
    public void testFind() throws Exception {
        ListEntryDao dao = new ListEntryDao("localhost:9160", AstyanaxDao.KEYSPACE);
        log(dao.find("naughty:USA", "PA", null));
        log(dao.find("naughty:USA", "CA", "94111"));
    }

    public void log(ColumnList<ListEntry> columns) {
        for (Column<ListEntry> column : columns) {
            ListEntry listEntry = column.getName();
            LOG.debug("---");
            LOG.debug("listEntry.state=>[" + listEntry.state + "]");
            LOG.debug("listEntry.zip=>[" + listEntry.zip + "]");
            LOG.debug("listEntry.childId=>[" + listEntry.childId + "]");
        }
    }
}
