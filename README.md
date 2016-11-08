# scanner

Needs a running instance of elastic search.  2.x is a good version.  Run "plugin install mbox/elasticsearch-head"
to install a basic UI.  It'll be available at http://localhost:9200/_plugin/head/ 

Look in the widget module for the start of the agent bits, there's a main method in there.  
Run that with "scan 1024 <some directory>" as the arguments.  That'll populate an index
in elastic search.
