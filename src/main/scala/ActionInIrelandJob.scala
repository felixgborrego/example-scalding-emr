package dummydata

import com.twitter.scalding._
import com.twitter.scalding.avro._

import scala.util.Random

class ActionInIrelandJob(args: Args) extends Job(args) {

  val IRELAND = Countries.Ireland.toString

  val data = UnpackedAvroSource("""s3://felix-buckettest1/random-event-actions.avro""").read
    .filter('country) { c: String => c == IRELAND }
    .groupAll { _.sortBy('action) }
    .write(Tsv("s3://felix-buckettest1/out.tsv"))

}

object Countries extends Enumeration {
  type CountryName = Value
  val Spain, Ireland, UK, USA, Japan = Value
  def randomName = Countries(Random.nextInt(Countries.values.size)).toString

}

object Actions extends Enumeration {
  type ActionName = Value
  val Buy, Visit = Value
  def randomName = Actions(Random.nextInt(Actions.values.size)).toString
}

// Othe sources
//val data = UnpackedAvroSource("""random-event-actions.avro""").read
//val data = UnpackedAvroSource("""s3://felix-buckettest1/random-event-actions.avro""").read
//val data = UnpackedAvroSource("""hdfs:///random-event-actions.avro""").read
// val data = UnpackedAvroSource("""random-event-actions.avro""").read
//.write(Tsv("s3://felix-buckettest1/out.tsv"))

