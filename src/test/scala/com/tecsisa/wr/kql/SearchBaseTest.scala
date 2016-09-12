package com.tecsisa.wr
package kql

import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.mappings.FieldType.{ DoubleType, IntegerType, StringType }
import com.sksamuel.elastic4s.testkit.{ ElasticMatchers, ElasticSugar }
import com.tecsisa.wr.kql.ast.Query
import com.tecsisa.wr.kql.parser.KqlParser
import org.scalatest.WordSpec
import org.scalatest.concurrent.Eventually

trait SearchBaseTest extends WordSpec with ElasticSugar with Eventually with ElasticMatchers {

  client.execute {
    create index "songs" mappings (
      mapping("song") fields (
        field("name") typed StringType,
        field("artist") typed StringType,
        field("composer") typed StringType index "not_analyzed",
        field("genre") typed StringType index "not_analyzed",
        field("year") typed IntegerType,
        field("price") typed DoubleType
      )
    )
  }.await

  client.execute {
    bulk(
      index into "songs/song" fields (
        "name"     -> "Paranoid Android",
        "artist"   -> "Radiohead",
        "composer" -> "Radiohead",
        "genre"    -> "Pop/Rock",
        "year"     -> 1997,
        "price"    -> 1.26
      ),
      index into "songs/song" fields (
        "name"     -> "Sinfonía núm. 1 en Do mayor, Op. 21. I Adagio molto - Allegro con brio",
        "artist"   -> "Simon Rattle // Berliner Philharmoniker",
        "composer" -> "Ludwig van Beethoven",
        "genre"    -> "Classical",
        "year"     -> 2016,
        "price"    -> 2.45
      ),
      index into "songs/song" fields (
        "name"     -> "So What",
        "artist"   -> "Miles Davis",
        "composer" -> "Miles Davis",
        "genre"    -> "Jazz",
        "year"     -> 1959,
        "price"    -> 1.99
      ),
      index into "songs/song" fields (
        "name"     -> "La Isla Bonita",
        "artist"   -> "Madonna",
        "composer" -> "Patrick Leonard",
        "genre"    -> "Pop/Rock",
        "year"     -> 1987,
        "price"    -> 1.29
      ),
      index into "songs/song" fields (
        "name"     -> "Symphony No.8 in E flat - \"Symphony of a Thousand\" Part One: Hymnus",
        "artist"   -> "Georg Solti // Chicago Symphony Orchestra",
        "composer" -> "Gustav Mahler",
        "genre"    -> "Classical",
        "year"     -> 1967,
        "price"    -> 2.11
      ),
      index into "songs/song" fields (
        "name"     -> "Do You Realize??",
        "artist"   -> "Flaming Lips",
        "composer" -> "Wayne Coyne",
        "genre"    -> "Pop/Rock",
        "year"     -> 2002,
        "price"    -> 1.29
      ),
      index into "songs/song" fields (
        "name"     -> "Don't Know Why",
        "artist"   -> "Norah Jones",
        "composer" -> "Jesse Harris",
        "genre"    -> "Jazz",
        "year"     -> 2002,
        "price"    -> 1.19
      ),
      index into "songs/song" fields (
        "name"     -> "Goldberg Variations: Aria",
        "artist"   -> "Glenn Gould",
        "composer" -> "Johann Sebastian Bach",
        "genre"    -> "Classical",
        "year"     -> 1955,
        "price"    -> 0.99
      ),
      index into "songs/song" fields (
        "name"     -> "Money For Nothing",
        "artist"   -> "Dire Straits",
        "composer" -> "Mark Knopfler",
        "genre"    -> "Pop/Rock",
        "year"     -> 1985,
        "price"    -> 1.29
      ),
      index into "songs/song" fields (
        "name"     -> "Smell Like Teen Spirit",
        "artist"   -> "Nirvana",
        "composer" -> "Nirvana",
        "genre"    -> "Pop/Rock",
        "year"     -> 1991,
        "price"    -> 1.34
      )
    )
  }.await

  refresh("songs")
  blockUntilCount(10, "songs")

  protected def q(qs: String): Query = KqlParser().parse(qs).get.value

}
