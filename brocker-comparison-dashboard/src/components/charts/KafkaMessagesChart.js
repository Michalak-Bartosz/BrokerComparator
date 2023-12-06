import React from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line } from "react-chartjs-2";
import { effect, signal } from "@preact/signals-react";

function KafkaMessagesChart() {
  ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
  );

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "top",
      },
      title: {
        display: true,
        text: "Chart.js Line Chart",
      },
    },
  };

  const getCount = (timestamp) => {
    // let newUsers = users.value.filter((user) => user.timestamp === timestamp);
    // return newUsers[0].countOfMessages;
    return 0;
  };
  const chartData = signal({});
  const changeChart = (labelsValue) => {
    chartData.value = {
      labels: labelsValue,
      datasets: [
        {
          label: "Dataset 1",
          data: labelsValue.map((label) => getCount(label)),
          borderColor: "rgb(255, 99, 132)",
          backgroundColor: "rgba(255, 99, 132, 0.5)",
        },
      ],
    };
  };

  // effect(() => {
  //   changeChart(users.value.map((user) => user.timestamp));
  // });

  return <Line options={options} data={chartData.value} />;
}

export default KafkaMessagesChart;
