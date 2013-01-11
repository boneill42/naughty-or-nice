drop keyspace northpole;

create keyspace northpole WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

use northpole;

create table children ( childId varchar, firstName varchar, lastName varchar, country varchar, state varchar, zip varchar, toys set<text>, primary key (childId ) );
create table naughtyOrNiceList ( standingByCountry varchar, state varchar, zip varchar, childId varchar, primary key (standingByCountry, state, zip, childId) );

insert into children (childId, firstName, lastName, country, state, zip) values ('owen.oneill', 'Owen', 'O''Neill', 'IRL', 'D', 'EI33');
insert into children (childId, firstName, lastName, country, state, zip) values ('collin.oneill', 'Collin', 'O''Neill', 'IRL', 'D', 'EI33');
insert into children (childId, firstName, lastName, country, state, zip) values ('richie.rich', 'Richie', 'Rich', 'USA', 'CA', '94333');
insert into children (childId, firstName, lastName, country, state, zip) values ('johny.b.good', 'Johny', 'Good', 'USA', 'CA', '94333');
insert into children (childId, firstName, lastName, country, state, zip) values ('bart.simpson', 'Bart', 'Simpson', 'USA', 'CA', '94111');
insert into children (childId, firstName, lastName, country, state, zip) values ('dennis.menace', 'Dennis', 'Menace', 'USA', 'CA', '94222');
insert into children (childId, firstName, lastName, country, state, zip) values ('michael.myers', 'Michael', 'Myers', 'USA', 'PA', '18964');

insert into naughtyOrNiceList (standingByCountry, state, zip, childId) values ('nice:IRL', 'D', 'EI33','owen.oneill');
insert into naughtyOrNiceList (standingByCountry, state, zip, childId) values ('nice:IRL', 'D', 'EI33', 'collin.oneill');
insert into naughtyOrNiceList (standingByCountry, state, zip, childId) values ('nice:USA', 'CA', '94333', 'richie.rich');
insert into naughtyOrNiceList (standingByCountry, state, zip, childId) values ('nice:USA', 'CA', '94333', 'johny.b.good');
insert into naughtyOrNiceList (standingByCountry, state, zip, childId) values ('naughty:USA', 'CA', '94111', 'bart.simpson');
insert into naughtyOrNiceList (standingByCountry, state, zip, childId) values ('naughty:USA', 'CA', '94222', 'dennis.menace');
insert into naughtyOrNiceList (standingByCountry, state, zip, childId) values ('naughty:USA', 'PA', '18964', 'michael.myers');

UPDATE children SET toys = toys + {'legos'}  WHERE childId = 'owen.oneill';
