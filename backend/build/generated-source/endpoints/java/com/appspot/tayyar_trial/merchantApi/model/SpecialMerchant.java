/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2017-09-05 21:06:36 UTC)
 * on 2017-09-21 at 20:06:41 UTC 
 * Modify at your own risk.
 */

package com.appspot.tayyar_trial.merchantApi.model;

/**
 * Model definition for SpecialMerchant.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the merchantApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class SpecialMerchant extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean active;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String city;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String imageURL;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Location location;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String phone;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer pricing;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer rating;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> regTokenList;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getActive() {
    return active;
  }

  /**
   * @param active active or {@code null} for none
   */
  public SpecialMerchant setActive(java.lang.Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCity() {
    return city;
  }

  /**
   * @param city city or {@code null} for none
   */
  public SpecialMerchant setCity(java.lang.String city) {
    this.city = city;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public SpecialMerchant setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public SpecialMerchant setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getImageURL() {
    return imageURL;
  }

  /**
   * @param imageURL imageURL or {@code null} for none
   */
  public SpecialMerchant setImageURL(java.lang.String imageURL) {
    this.imageURL = imageURL;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Location getLocation() {
    return location;
  }

  /**
   * @param location location or {@code null} for none
   */
  public SpecialMerchant setLocation(Location location) {
    this.location = location;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public SpecialMerchant setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPhone() {
    return phone;
  }

  /**
   * @param phone phone or {@code null} for none
   */
  public SpecialMerchant setPhone(java.lang.String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPricing() {
    return pricing;
  }

  /**
   * @param pricing pricing or {@code null} for none
   */
  public SpecialMerchant setPricing(java.lang.Integer pricing) {
    this.pricing = pricing;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getRating() {
    return rating;
  }

  /**
   * @param rating rating or {@code null} for none
   */
  public SpecialMerchant setRating(java.lang.Integer rating) {
    this.rating = rating;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getRegTokenList() {
    return regTokenList;
  }

  /**
   * @param regTokenList regTokenList or {@code null} for none
   */
  public SpecialMerchant setRegTokenList(java.util.List<java.lang.String> regTokenList) {
    this.regTokenList = regTokenList;
    return this;
  }

  @Override
  public SpecialMerchant set(String fieldName, Object value) {
    return (SpecialMerchant) super.set(fieldName, value);
  }

  @Override
  public SpecialMerchant clone() {
    return (SpecialMerchant) super.clone();
  }

}
