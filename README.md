# scanner

Needs a running instance of elastic search.  2.x is a good version.  Run "plugin install mbox/elasticsearch-head"
to install a basic UI.  It'll be available at http://localhost:9200/_plugin/head/ 

Look in the widget module for the start of the agent bits, there's a main method in there.  
Run that with "scan 1024 <some directory>" as the arguments.  That'll create and populate
an index in the elastic search instance.  The index name will be the same as your machine's 
hostname but in lower case (weird limitation in elastic search). 

Use the aggregate functionality in elastic search to find duplicates based on the computed 
hash.  Here's a sample query:

{"size":0,"query":{"bool":{"must_not":[{"term":{"hashes.1024":"23a2386b26eb636afabaebb0c836adf4"}}]}},"aggs":{"duplicates":{"terms":{"field":"hashes.1024"}}}}
