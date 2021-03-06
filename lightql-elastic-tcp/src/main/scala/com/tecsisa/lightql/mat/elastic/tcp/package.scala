/*
 * Copyright (C) 2016 - 2018 TECNOLOGIA, SISTEMAS Y APLICACIONES S.L. <http://www.tecsisa.com>
 */

package com.tecsisa.lightql
package mat
package elastic

import com.sksamuel.elastic4s.searches.queries.BuildableTermsQueryImplicits
import com.sksamuel.elastic4s.searches.queries.term.BuildableTermsQuery

package object tcp extends ElasticMaterializerHelpers {
  @deprecated("TCP will be removed in Elasticsearch 7. Use HTTP instead.", "6.0.0")
  implicit val elasticMaterializer: ElasticMaterializer =
    new ElasticMaterializer with BuildableTermsQueryImplicits {
      protected implicit val btq: BuildableTermsQuery[AnyRef] = AnyRefBuildableTermsQuery
    }
}
