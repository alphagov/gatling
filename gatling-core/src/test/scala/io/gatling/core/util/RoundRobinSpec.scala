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
package io.gatling.core.util

import org.scalatest.{ FlatSpec, Matchers }

class RoundRobinSpec extends FlatSpec with Matchers {

  "round robin" should "work fine with non empty Iterable" in {

    val rr = RoundRobin(Array(1, 2, 3))

    rr.next shouldBe 1
    rr.next shouldBe 2
    rr.next shouldBe 3
    rr.next shouldBe 1
    rr.next shouldBe 2
    rr.next shouldBe 3
  }

  it should "throw NoSuchElementException with iterating on an empty Iterable" in {

    val rr = RoundRobin(Array.empty[Int])

    a[NoSuchElementException] should be thrownBy rr.next
  }
}
