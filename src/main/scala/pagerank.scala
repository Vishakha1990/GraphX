package org.apache.spark.examples.graphx
import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.graphx.lib._
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import scala.collection.mutable
import org.apache.spark.graphx.PartitionStrategy._
import org.apache.spark.internal.Logging
import org.apache.spark.storage.StorageLevel
import org.apache.spark.graphx.GraphLoader


object pagerank { 
def main(args: Array[String]) {
//    if (args.length < 1) {
//      System.err.println("Usage: SparkPageRank <file> <iter>")
//      System.exit(1)
//    }

//val file = argv[1]
//val iter = argv[2]
val conf = new SparkConf().setAppName("Pagerank").set("spark.driver.memory", "4g").set("spark.eventLog.enabled", "true").set("spark.eventLog.dir", "hdfs:/user/ubuntu/graphx-logs").set("spark.executor.memory", "4g").set("spark.executor.cores", "4").set("spark.tasks.cpus", "1").set("spark.history.fs.logDirectory", "hdfs:/user/ubuntu/graphx-logs")
val spark = new SparkContext(conf) 


val PageRankGraph = GraphLoader.edgeListFile(spark, "/user/ubuntu/soc-LiveJournal1.txt").mapVertices((id, attr)=> (1.0, 0.0))

var finalGraph = PageRankGraph.outerJoinVertices(PageRankGraph.outDegrees) {(vid,vdata,deg) => (1.0,deg.getOrElse(0))}
var a = 0;
for( a <- 1 to 20)
{
val rankRDD = finalGraph.aggregateMessages[Double](
	triplet => {
	if(triplet.srcAttr._2 > 0)
		{
			triplet.sendToDst(triplet.srcAttr._1/triplet.srcAttr._2)
		}
        else
                {
                        triplet.sendToDst(0)
                }

	}, 
	
	(a, b) => (a + b)
	).mapValues( (id, value) => value match { case (count) => (0.15 + 0.85*count)} )

finalGraph = finalGraph.outerJoinVertices(rankRDD) {(vid,vdata,rank) => (rank.getOrElse(1.0) , vdata._2)}
}
finalGraph.vertices.collect.foreach(println(_))

spark.stop()

}

}




