/**
 * Copyright 2011-2014 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.http.cookie

import org.scalatest.{ FlatSpec, Matchers }

import com.ning.http.client.cookie.CookieDecoder.decode
import com.ning.http.client.uri.UriComponents

import io.gatling.core.session.Session

class CookieHandlingSpec extends FlatSpec with Matchers {

  val originalCookie = decode("ALPHA=VALUE1; Domain=docs.foo.com; Path=/; Expires=Wed, 13-Jan-2021 22:23:01 GMT; Secure; HttpOnly")
  val originalDomain = "docs.foo.com"
  val originalCookieJar = new CookieJar(Map(CookieKey("ALPHA", originalDomain, "/") -> StoredCookie(originalCookie, hostOnly = true, persistent = true, 0L)))
  val originalSession = Session("scenarioName", "1", Map(CookieHandling.CookieJarAttributeName -> originalCookieJar))

  val emptySession = Session("scenarioName", "2")

  "getStoredCookies" should "be able to get a cookie from session" in {
    CookieHandling.getStoredCookies(originalSession, "https://docs.foo.com/accounts").map(x => x.getValue) shouldBe List("VALUE1")
  }

  it should "be called with an empty session" in {
    CookieHandling.getStoredCookies(emptySession, "https://docs.foo.com/accounts") shouldBe empty
  }

  "storeCookies" should "be able to store a cookie in an empty session" in {
    val newCookie = decode("ALPHA=VALUE1; Domain=docs.foo.com; Path=/accounts; Expires=Wed, 13-Jan-2021 22:23:01 GMT; Secure; HttpOnly")
    CookieHandling.storeCookies(emptySession, UriComponents.create("https://docs.foo.com/accounts"), List(newCookie))

    CookieHandling.getStoredCookies(emptySession, "https://docs.foo.com/accounts") shouldBe empty
  }
}
