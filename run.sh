sbt package
#~/software/spark-2.0.1-bin-hadoop2.6/bin/spark-submit --class "SimpleApp" --master "spark://10.254.0.169:7077" target/scala-2.11/simple-project_2.11-1.0.jar
 ~/software/spark-2.0.1-bin-hadoop2.6/bin/spark-submit --class "org.apache.spark.examples.graphx.pagerank" --master "spark://10.254.0.169:7077" target/scala-2.11/pagerank-project_2.11-1.0.jar
