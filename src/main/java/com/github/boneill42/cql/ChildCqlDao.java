package com.github.boneill42.cql;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.Query;

public class ChildCqlDao {
    private static final Logger LOG = LoggerFactory.getLogger(ChildCqlDao.class);

    public static final String KEYSPACE = "northpole";
    public static final String TABLE = "children";
    protected static Cluster cluster;
    protected static Session session;

    public ChildCqlDao(String host) {
        try {
            cluster = Cluster.builder().addContactPoints(host).build();
            session = cluster.connect();
            session.execute("USE " + KEYSPACE);
        } catch (NoHostAvailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToy(String childId, String toy) throws NoHostAvailableException {
//        String query = "UPDATE northpole.children SET toys = toys + {'" + toy + "'} WHERE childid = '" + childId + "'";
        Query update = update(KEYSPACE, TABLE).with(add("toys", toy)).where(eq("childId", childId));
//        LOG.debug("Raw [" + query + "]");
        LOG.debug("QueryBuilder [" + update.toString() + "]");
        session.execute(update);
    }

}
