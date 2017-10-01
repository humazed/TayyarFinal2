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
 * Model definition for DeliveryRequest.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the customerApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class DeliveryRequest extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double charge;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long customerId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double distanceDriverDrived;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean driverAcceptsOrder;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean driverConfirmedPickUP;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long driverId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.util.List<java.lang.Long> driversWhoRefusedIDs;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String generalInstructions;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean merchantAcceptsOrder;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long merchantId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean orderDelivered;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double timeDriverSpent;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double tip;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getCharge() {
    return charge;
  }

  /**
   * @param charge charge or {@code null} for none
   */
  public DeliveryRequest setCharge(java.lang.Double charge) {
    this.charge = charge;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getCustomerId() {
    return customerId;
  }

  /**
   * @param customerId customerId or {@code null} for none
   */
  public DeliveryRequest setCustomerId(java.lang.Long customerId) {
    this.customerId = customerId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getDistanceDriverDrived() {
    return distanceDriverDrived;
  }

  /**
   * @param distanceDriverDrived distanceDriverDrived or {@code null} for none
   */
  public DeliveryRequest setDistanceDriverDrived(java.lang.Double distanceDriverDrived) {
    this.distanceDriverDrived = distanceDriverDrived;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getDriverAcceptsOrder() {
    return driverAcceptsOrder;
  }

  /**
   * @param driverAcceptsOrder driverAcceptsOrder or {@code null} for none
   */
  public DeliveryRequest setDriverAcceptsOrder(java.lang.Boolean driverAcceptsOrder) {
    this.driverAcceptsOrder = driverAcceptsOrder;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getDriverConfirmedPickUP() {
    return driverConfirmedPickUP;
  }

  /**
   * @param driverConfirmedPickUP driverConfirmedPickUP or {@code null} for none
   */
  public DeliveryRequest setDriverConfirmedPickUP(java.lang.Boolean driverConfirmedPickUP) {
    this.driverConfirmedPickUP = driverConfirmedPickUP;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getDriverId() {
    return driverId;
  }

  /**
   * @param driverId driverId or {@code null} for none
   */
  public DeliveryRequest setDriverId(java.lang.Long driverId) {
    this.driverId = driverId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Long> getDriversWhoRefusedIDs() {
    return driversWhoRefusedIDs;
  }

  /**
   * @param driversWhoRefusedIDs driversWhoRefusedIDs or {@code null} for none
   */
  public DeliveryRequest setDriversWhoRefusedIDs(java.util.List<java.lang.Long> driversWhoRefusedIDs) {
    this.driversWhoRefusedIDs = driversWhoRefusedIDs;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getGeneralInstructions() {
    return generalInstructions;
  }

  /**
   * @param generalInstructions generalInstructions or {@code null} for none
   */
  public DeliveryRequest setGeneralInstructions(java.lang.String generalInstructions) {
    this.generalInstructions = generalInstructions;
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
  public DeliveryRequest setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getMerchantAcceptsOrder() {
    return merchantAcceptsOrder;
  }

  /**
   * @param merchantAcceptsOrder merchantAcceptsOrder or {@code null} for none
   */
  public DeliveryRequest setMerchantAcceptsOrder(java.lang.Boolean merchantAcceptsOrder) {
    this.merchantAcceptsOrder = merchantAcceptsOrder;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMerchantId() {
    return merchantId;
  }

  /**
   * @param merchantId merchantId or {@code null} for none
   */
  public DeliveryRequest setMerchantId(java.lang.Long merchantId) {
    this.merchantId = merchantId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getOrderDelivered() {
    return orderDelivered;
  }

  /**
   * @param orderDelivered orderDelivered or {@code null} for none
   */
  public DeliveryRequest setOrderDelivered(java.lang.Boolean orderDelivered) {
    this.orderDelivered = orderDelivered;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getTimeDriverSpent() {
    return timeDriverSpent;
  }

  /**
   * @param timeDriverSpent timeDriverSpent or {@code null} for none
   */
  public DeliveryRequest setTimeDriverSpent(java.lang.Double timeDriverSpent) {
    this.timeDriverSpent = timeDriverSpent;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getTip() {
    return tip;
  }

  /**
   * @param tip tip or {@code null} for none
   */
  public DeliveryRequest setTip(java.lang.Double tip) {
    this.tip = tip;
    return this;
  }

  @Override
  public DeliveryRequest set(String fieldName, Object value) {
    return (DeliveryRequest) super.set(fieldName, value);
  }

  @Override
  public DeliveryRequest clone() {
    return (DeliveryRequest) super.clone();
  }

}
