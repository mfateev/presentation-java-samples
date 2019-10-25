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

import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.common.RetryOptions;
import com.uber.cadence.workflow.Workflow;
import java.time.Duration;

public class AccountTransferWorkflowImpl implements AccountTransferWorkflow {

  private final ActivityOptions options =
      new ActivityOptions.Builder()
          .setStartToCloseTimeout(Duration.ofSeconds(5))
          .setScheduleToStartTimeout(Duration.ofHours(1))
          .setRetryOptions(
              new RetryOptions.Builder()
                  .setInitialInterval(Duration.ofSeconds(1))
                  .setExpiration(Duration.ofMinutes(10))
                  .setMaximumInterval(Duration.ofSeconds(10))
                  .build())
          .build();
  private final Account account = Workflow.newActivityStub(Account.class, options);

  @Override
  public void transfer(
      String fromAccountId, String toAccountId, String referenceId, int amountCents) {
    account.withdraw(fromAccountId, referenceId, amountCents);
    account.deposit(toAccountId, referenceId, amountCents);
  }
}
