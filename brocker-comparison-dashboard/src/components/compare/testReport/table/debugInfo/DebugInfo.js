import { Table } from "flowbite-react";
import React, { useState } from "react";
import { getDateFromTimestampString } from "../../../../util/DateTimeUtil";
import MemoryMetricModal from "./metrics/memoryMetric/MemoryMetricModal";
import CpuMetricModal from "./metrics/cpuMetric/CpuMetricModal";

function DebugInfo({ debugInfo, index }) {
  const [openProducerMemoryMetricModal, setOpenProducerMemoryMetricModal] =
    useState(false);
  const [openConsumerMemoryMetricModal, setOpenConsumerMemoryMetricModal] =
    useState(false);
  const [openProducerCpuMetricModal, setOpenProducerCpuMetricModal] =
    useState(false);
  const [openConsumerCpuMetricModal, setOpenConsumerCpuMetricModal] =
    useState(false);
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
      <Table.Cell>{debugInfo.formattedDeltaTimestamp}</Table.Cell>
      <Table.Cell>{debugInfo.countOfProducedMessages}</Table.Cell>
      <Table.Cell>{debugInfo.countOfConsumedMessages}</Table.Cell>
      <Table.Cell>
        <button
          id="show-test-data-button"
          className="flex mx-auto px-4 py-1 outline outline-offset-2 outline-blue-700 rounded-md bg-blue-800 hover:bg-blue-600 text-white font-bold"
          onClick={() => setOpenProducerMemoryMetricModal(true)}
        >
          Show
        </button>
        <MemoryMetricModal
          memoryMetric={debugInfo.producerMemoryMetrics}
          app={"Producer"}
          openModal={openProducerMemoryMetricModal}
          setOpenModal={setOpenProducerMemoryMetricModal}
        />
      </Table.Cell>
      <Table.Cell>
        <button
          id="show-test-data-button"
          className="flex mx-auto px-4 py-1 outline outline-offset-2 outline-blue-700 rounded-md bg-blue-800 hover:bg-blue-600 text-white font-bold"
          onClick={() => setOpenConsumerMemoryMetricModal(true)}
        >
          Show
        </button>
        <MemoryMetricModal
          memoryMetric={debugInfo.consumerMemoryMetrics}
          app={"Consumer"}
          openModal={openConsumerMemoryMetricModal}
          setOpenModal={setOpenConsumerMemoryMetricModal}
        />
      </Table.Cell>
      <Table.Cell>
        <button
          id="show-test-data-button"
          className="flex mx-auto px-4 py-1 outline outline-offset-2 outline-blue-700 rounded-md bg-blue-800 hover:bg-blue-600 text-white font-bold"
          onClick={() => setOpenProducerCpuMetricModal(true)}
        >
          Show
        </button>
        <CpuMetricModal
          cpuMetric={debugInfo.producerCPUMetrics}
          app={"Producer"}
          openModal={openProducerCpuMetricModal}
          setOpenModal={setOpenProducerCpuMetricModal}
        />
      </Table.Cell>
      <Table.Cell>
        <button
          id="show-test-data-button"
          className="flex mx-auto px-4 py-1 outline outline-offset-2 outline-blue-700 rounded-md bg-blue-800 hover:bg-blue-600 text-white font-bold"
          onClick={() => setOpenConsumerCpuMetricModal(true)}
        >
          Show
        </button>
        <CpuMetricModal
          cpuMetric={debugInfo.consumerCPUMetrics}
          app={"Consumer"}
          openModal={openConsumerCpuMetricModal}
          setOpenModal={setOpenConsumerCpuMetricModal}
        />
      </Table.Cell>
    </Table.Row>
  );
}

export default DebugInfo;
