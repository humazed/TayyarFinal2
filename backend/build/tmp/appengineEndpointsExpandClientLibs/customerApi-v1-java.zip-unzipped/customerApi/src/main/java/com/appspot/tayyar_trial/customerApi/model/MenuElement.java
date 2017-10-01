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
 * on 2017-09-21 at 20:06:47 UTC 
 * Modify at your own risk.
 */

package com.appspot.tayyar_trial.customerApi.model;

/**
 * Model definition for MenuElement.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the customerApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class MenuElement extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long categoryID;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String categoryName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String imageURL;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getCategoryID() {
    return categoryID;
  }

  /**
   * @param categoryID categoryID or {@code null} for none
   */
  public MenuElement setCategoryID(java.lang.Long categoryID) {
    this.categoryID = categoryID;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCategoryName() {
    return categoryName;
  }

  /**
   * @param categoryName categoryName or {@code null} for none
   */
  public MenuElement setCategoryName(java.lang.String categoryName) {
    this.categoryName = categoryName;
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
  public MenuElement setImageURL(java.lang.String imageURL) {
    this.imageURL = imageURL;
    return this;
  }

  @Override
  public MenuElement set(String fieldName, Object value) {
    return (MenuElement) super.set(fieldName, value);
  }

  @Override
  public MenuElement clone() {
    return (MenuElement) super.clone();
  }

}
