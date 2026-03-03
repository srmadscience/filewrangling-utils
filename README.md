# filewrangling-utils - Utility classes for fixing flat CSV files

Imagine you need to load a set of flat CSV files. Each file conveniently has a header row, but you can't use
it 'as is', because:

* Some of the column names are illegal or aren't recognized by your platform
* You need to map NULL in some cases
* Some header lines need to be all lower case
* Some fields need another value prepended
* etc etc..

This code will help you with this issue.

# How it works

You have an [input file](https://github.com/srmadscience/filewrangling-utils/blob/main/testdata/ontime_subset.csv) you
wish to change. Like this one:

````
FL_DATE,OP_UNIQUE_CARRIER,TAIL_NUM,OP_CARRIER_FL_NUM,ORIGIN,ORIGIN_CITY_NAME,DEST,DEST_CITY_NAME,CRS_DEP_TIME,DEP_TIME,DEP_DELAY,CRS_ARR_TIME,CRS_ELAPSED_TIME,DISTANCE
7/1/2021 12:00:00 AM,9E,N131EV,4979,ATL,"Atlanta, GA",DAY,"Dayton, OH",1632,1632,0.00,1809,97.00,432.00
````

You need to:

* Map the header to lower case
* Make sure NULL is mapped to zero for numeric fields
* Make sure TAIL_NUM is mapped to unknown if null
* Add "N" to the front of TAIL_NUM
* Convert the wacky American date format ````7/1/2021 12:00:00 AM```` into something you can parse

You can do this using this code and the
following [JSON file](https://github.com/srmadscience/filewrangling-utils/blob/main/testdata/ontime_subset.json):

````
{
"lineMappings":[
{"requestType":"LineForceToLowerCase","props":{"endLine":1,"startLine":1}}
]
, "fieldMappings":[
{"requestType":"FieldFixDateFormat","props":{"outputLocale":"en_GB","inputLocale":"en_US","inputFormat":"MM/dd/yyyy KK:mm:ss a","outputFormat":"dd/MM/yyyy"},"fieldNames":["FL_DATE"]}
,{"requestType":"FieldKeep","props":{}, "fieldNames":["OP_UNIQUE_CARRIER,TAIL_NUM,OP_CARRIER_FL_NUM,ORIGIN,ORIGIN_CITY_NAME,DEST,DEST_CITY_NAME,CRS_DEP_TIME,DEP_TIME,DEP_DELAY,CRS_ARR_TIME,CRS_ELAPSED_TIME,DISTANCE"] }
,{"requestType":"FieldPrepend","props":{"thingToPrepend":"N"}, "fieldNames":["N-NUMBER"] }
,{"requestType":"FieldNvl","props":{"nvlValue":"UNKNOWN"}, "fieldNames":["TAIL_NUM"] }
,{"requestType":"FieldNvl","props":{"nvlValue":"0"}, "fieldNames":["CRS_DEP_TIME,DEP_TIME,DEP_DELAY,CRS_ARR_TIME,CRS_ELAPSED_TIME,DISTANCE"] }
] 
} 
````

The above JSON in a GSONized version
of [FileMapping](https://github.com/srmadscience/filewrangling-utils/blob/main/main/src/ie/rolfe/filewrangling/model/FileMapping.java),
which is
a holder for two arrays
of [WranglerRequest](https://github.com/srmadscience/filewrangling-utils/blob/main/main/src/ie/rolfe/filewrangling/model/WranglerRequest.java),
one for Field changes and one line changes. These requests are used to instantiate instances of the classes in
[ie.rolfe.filewrangling.impl](https://github.com/srmadscience/filewrangling-utils/tree/main/main/src/ie/rolfe/filewrangling/impl)
at runtime. These classes do the actual work.

Call FileWrangler with the following parameters:

```
input_file_name output_file_name json_file_name
```

And you get:

````
fl_date,op_unique_carrier,tail_num,op_carrier_fl_num,origin,origin_city_name,dest,dest_city_name,crs_dep_time,dep_time,dep_delay,crs_arr_time,crs_elapsed_time,distance
01/04/2023,9E,N131EV,4743,DTW,"Detroit, MI",ORF,"Norfolk, VA",1020,1020,0.00,1211,111.00,529.00
````