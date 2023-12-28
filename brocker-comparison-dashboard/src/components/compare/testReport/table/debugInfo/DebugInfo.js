import { Table } from "flowbite-react";
import React from "react";
import { getDateFromTimestampString } from "../../../../util/DateUtil";
import moment from "moment";

function DebugInfo({ debugInfo, index }) {
  return (
    <Table.Row className="bg-white dark:border-gray-700 dark:bg-gray-800">
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {index}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {debugInfo.uuid}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {debugInfo.testUUID}
      </Table.Cell>
      <Table.Cell>{debugInfo.brokerType}</Table.Cell>
      <Table.Cell>{debugInfo.testStatusPercentage}</Table.Cell>
      <Table.Cell className="whitespace-nowrap">
        {getDateFromTimestampString(debugInfo.producedTimestamp).toISOString()}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap">
        {getDateFromTimestampString(debugInfo.consumedTimestamp).toISOString()}
      </Table.Cell>
      <Table.Cell>
        {moment
          .duration(debugInfo.deltaTimestamp)
          .as("milliseconds")
          .toFixed(3)}
      </Table.Cell>
      <Table.Cell>{debugInfo.countOfProducedMessages}</Table.Cell>
      <Table.Cell>{debugInfo.countOfConsumedMessages}</Table.Cell>
      <Table.Cell>Show Producer Memory Metrics</Table.Cell>
      <Table.Cell>Show Producer CPU Metrics</Table.Cell>
      <Table.Cell>Show Consumer Memory Metrics</Table.Cell>
      <Table.Cell>Show Consumer CPU Metrics</Table.Cell>
    </Table.Row>
  );
}

export default DebugInfo;
