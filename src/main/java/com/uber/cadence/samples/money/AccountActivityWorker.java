/*
 *  Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *  Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
 *  use this file except in compliance with the License. A copy of the License is
 *  located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 *  or in the "license" file accompanying this file. This file is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package com.uber.cadence.samples.money;

import static com.uber.cadence.samples.common.SampleConstants.DOMAIN;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.uber.cadence.worker.Worker;
import java.util.Scanner;

public class AccountActivityWorker {

  static final String TASK_LIST = "AcccountTransfer";

  @SuppressWarnings("CatchAndPrintStackTrace")
  public static void main(String[] args) {
    // Get worker to poll the common task list.
    Worker.Factory factory = new Worker.Factory(DOMAIN);
    Worker worker = factory.newWorker(TASK_LIST);
    Scanner input = new Scanner(System.in, UTF_8.name());
    Account account = new AccountImpl(input);
    worker.registerActivitiesImplementations(account);

    // Start all workers created by this factory.
    factory.start();
    System.out.println("Activity Worker started for task list: " + TASK_LIST);
  }
}
