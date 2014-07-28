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
package io.gatling.core.config

import org.scalatest.{ FlatSpec, Matchers, OptionValues }

class ProtocolSpec extends FlatSpec with Matchers with OptionValues {

  case class FooProtocol(foo: String) extends Protocol

  case class BarProtocol(bar: String) extends Protocol

  "building registry" should "return the configuration when 1 configuration" in {
    Protocols(new FooProtocol("foo")).getProtocol[FooProtocol].value.foo shouldBe "foo"
  }

  it should "return the configurations when 2 different configurations" in {
    val protocols = Protocols(new FooProtocol("foo"), new BarProtocol("bar"))
    protocols.getProtocol[FooProtocol].value.foo shouldBe "foo"
    protocols.getProtocol[BarProtocol].value.bar shouldBe "bar"
  }

  it should "not fail when no configuration" in {
    Protocols().getProtocol[FooProtocol] shouldBe None
  }

  it should "override with latest when multiple configurations of the same type" in {
    Protocols(new FooProtocol("foo1"), new FooProtocol("foo2")).getProtocol[FooProtocol].value.foo shouldBe "foo2"
  }
}
