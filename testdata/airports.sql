CREATE TABLE airports
(id text 
,ident text 
,type text 
,name text
,latitude_deg REAL
,longitude_deg REAL
,elevation_ft SMALLINT
,continent TEXT 
,iso_country TEXT
,iso_region TEXT
,municipality TEXT
,scheduled_service TEXT
,icao_code TEXT
,iata_code TEXT NOT NULL
,gps_code TEXT
,local_code TEXT
,home_link TEXT
,wikipedia_link TEXT
,keywords TEXT
,PRIMARY KEY (iata_code)
);
