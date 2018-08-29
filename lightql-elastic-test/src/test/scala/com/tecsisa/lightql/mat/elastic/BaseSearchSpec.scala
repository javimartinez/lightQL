/*
 * Copyright (C) 2016 - 2018 TECNOLOGIA, SISTEMAS Y APLICACIONES S.L. <http://www.tecsisa.com>
 */

package com.tecsisa.lightql
package mat
package elastic

import com.sksamuel.elastic4s.analyzers.KeywordAnalyzer
import com.sksamuel.elastic4s.testkit.{ ClassloaderLocalNodeProvider, HttpElasticSugar }
import com.tecsisa.lightql.ast.Query
import com.tecsisa.lightql.parser.LightqlParser
import org.scalatest._
import org.scalatest.concurrent.Eventually

trait BaseSearchSpec
    extends WordSpec
    with Eventually
    with HttpElasticSugar
    with ClassloaderLocalNodeProvider
    with BeforeAndAfterAll {

  override protected def beforeAll(): Unit = {
    http.execute {
      createIndex("songs") mappings (
        mapping("song") as (
          textField("name"),
          textField("artist"),
          textField("composer") analyzer KeywordAnalyzer,
          textField("genre") analyzer KeywordAnalyzer,
          objectField("date") fields (
            dateField("full"),
            intField("year"),
            dateField("yearMonthDay").format("strict_year_month_day")
          ),
          doubleField("price"),
          nestedField("stats") fields (
            objectField("rate") fields doubleField("stars")
          )
        )
      )
    }.await

    http.execute {
      bulk(
        indexInto("songs/song") fields (
          "name"     -> "Paranoid Android",
          "artist"   -> "Radiohead",
          "composer" -> "Radiohead",
          "genre"    -> "Pop/Rock",
          "date"     -> Map("full" -> "2016-01-05", "year" -> 1997, "yearMonthDay" -> "2016-01-05"),
          "price"    -> 1.26,
          "stats"    -> Map("rate" -> Map("stars" -> 4.5))
        ),
        indexInto("songs/song") fields (
          "name"     -> "Sinfonía núm. 1 en Do mayor, Op. 21. I Adagio molto - Allegro con brio",
          "artist"   -> "Simon Rattle // Berliner Philharmoniker",
          "composer" -> "Ludwig van Beethoven",
          "genre"    -> "Classical",
          "date" -> Map(
            "full"         -> "2016-02-06T00:00:00.000Z",
            "year"         -> 2016,
            "yearMonthDay" -> "2016-02-06"),
          "year"  -> 2016,
          "price" -> 2.45,
          "stats" -> Map("rate" -> Map("stars" -> 3.5))
        ),
        indexInto("songs/song") fields (
          "name"     -> "So What",
          "artist"   -> "Miles Davis",
          "composer" -> "Miles Davis",
          "genre"    -> "Jazz",
          "date" -> Map(
            "full"         -> "1959-08-17T00:00:00.000Z",
            "year"         -> 1959,
            "yearMonthDay" -> "1959-08-17"),
          "price" -> 1.99,
          "stats" -> Map("rate" -> Map("stars" -> 5.0))
        ),
        indexInto("songs/song") fields (
          "name"     -> "La Isla Bonita",
          "artist"   -> "Madonna",
          "composer" -> "Patrick Leonard",
          "genre"    -> "Pop/Rock",
          "date" -> Map(
            "full"         -> "1987-02-25T00:00:00.000Z",
            "year"         -> 1987,
            "yearMonthDay" -> "1987-02-25"),
          "price" -> 1.29,
          "stats" -> Map("rate" -> Map("stars" -> 2.75))
        ),
        indexInto("songs/song") fields (
          "name"     -> "Symphony No.8 in E flat - \"Symphony of a Thousand\" Part One: Hymnus",
          "artist"   -> "Georg Solti // Chicago Symphony Orchestra",
          "composer" -> "Gustav Mahler",
          "genre"    -> "Classical",
          "date" -> Map(
            "full"         -> "1967-10-04T00:00:00.000Z",
            "year"         -> 1967,
            "yearMonthDay" -> "1967-10-04"),
          "price" -> 2.11,
          "stats" -> Map("rate" -> Map("stars" -> 3.25))
        ),
        indexInto("songs/song") fields (
          "name"     -> "Do You Realize??",
          "artist"   -> "Flaming Lips",
          "composer" -> "Wayne Coyne",
          "genre"    -> "Pop/Rock",
          "date" -> Map(
            "full"         -> "2002-08-19T00:00:00.000Z",
            "year"         -> 2002,
            "yearMonthDay" -> "2002-08-19"),
          "price" -> 1.29,
          "stats" -> Map("rate" -> Map("stars" -> 4.25))
        ),
        indexInto("songs/song") fields (
          "name"     -> "Don't Know Why",
          "artist"   -> "Norah Jones",
          "composer" -> "Jesse Harris",
          "genre"    -> "Jazz",
          "date" -> Map(
            "full"         -> "2002-07-01T00:00:00.000Z",
            "year"         -> 2002,
            "yearMonthDay" -> "2002-07-01"),
          "price" -> 1.19,
          "stats" -> Map("rate" -> Map("stars" -> 1.5))
        ),
        indexInto("songs/song") fields (
          "name"     -> "Goldberg Variations: Aria",
          "artist"   -> "Glenn Gould",
          "composer" -> "Johann Sebastian Bach",
          "genre"    -> "Classical",
          "date" -> Map(
            "full"         -> "1955-05-22T00:00:00.000Z",
            "year"         -> 1955,
            "yearMonthDay" -> "1955-05-22"),
          "price" -> 0.99,
          "stats" -> Map("rate" -> Map("stars" -> 3.0))
        ),
        indexInto("songs/song") fields (
          "name"     -> "Money For Nothing",
          "artist"   -> "Dire Straits",
          "composer" -> "Mark Knopfler",
          "genre"    -> "Pop/Rock",
          "date"     -> Map("full" -> "1985-11-02", "year" -> 1985, "yearMonthDay" -> "1985-11-02"),
          "price"    -> 1.29,
          "stats"    -> Map("rate" -> Map("stars" -> 3.5))
        ),
        indexInto("songs/song") fields (
          "name"     -> "Smell Like Teen Spirit",
          "artist"   -> "Nirvana",
          "composer" -> "Nirvana",
          "genre"    -> "Pop/Rock",
          "date" -> Map(
            "full"         -> "1991-09-10T00:00:00.000Z",
            "year"         -> 1991,
            "yearMonthDay" -> "1991-09-10"),
          "price" -> 1.34,
          "stats" -> Map("rate" -> Map("stars" -> 5.0))
        )
      )
    }.await

    refresh("songs")
    blockUntilCount(10, "songs")
  }

  protected def q(qs: String): Query = LightqlParser.parse(qs).get.value

}
