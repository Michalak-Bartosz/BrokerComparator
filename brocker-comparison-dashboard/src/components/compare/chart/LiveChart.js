import React, { useEffect, useState } from "react";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";

function LiveChart(props) {
  const [chartOptions, setChartOption] = useState({
    chart: {
      backgroundColor: "rgba(255,255,255, 0.6)",
      borderRadius: "10",
      type: "area",
    },
    title: {
      text: `<h1 id="chart-title">${
        props.chartName ? props.chartName : ""
      }</h1>`,
      align: "center",
      style: {
        fontSize: "24px",
      },
    },
    accessibility: {
      enabled: false,
    },
    plotOptions: {
      series: {
        color: props.datasetColor
          ? props.datasetColor.rgb
          : Highcharts.getOptions().colors[0],
        fillColor: {
          linearGradient: [0, 0, 0, 300],
          stops: [
            [
              0,
              props.datasetColor
                ? props.datasetColor.rgb
                : Highcharts.getOptions().colors[0],
            ],
            [
              1,
              props.datasetColor
                ? props.datasetColor.rgba
                : Highcharts.color(Highcharts.getOptions().colors[0])
                    .setOpacity(0)
                    .get("rgba"),
            ],
          ],
        },
      },
    },
    xAxis: {
      title: {
        text: props.xAxisName ? props.xAxisName : "Timestamp",
        style: {
          fontSize: "18px",
        },
      },
      categories: props.data.map((v) => {
        return Highcharts.dateFormat(
          //   "%Y-%m-%d
          "%H:%M:%S.%L",
          new Date(v.xVal).getTime()
        );
      }),
    },
    yAxis: {
      title: {
        text: props.yAxisName ? props.yAxisName : "Values",
        style: {
          fontSize: "18px",
        },
      },
    },
    series: [
      {
        name: props.datasetName ? props.datasetName : "",
        data: props.data.map((v) => {
          return v.yVal;
        }),
      },
    ],
  });

  useEffect(() => {
    const updateChartOptions = () => {
      setChartOption({
        plotOptions: {
          series: {
            color: props.datasetColor
              ? props.datasetColor.rgb
              : Highcharts.getOptions().colors[0],
            fillColor: {
              linearGradient: [0, 0, 0, 200],
              stops: [
                [
                  0,
                  props.datasetColor
                    ? props.datasetColor.rgb
                    : Highcharts.getOptions().colors[0],
                ],
                [
                  1,
                  props.datasetColor
                    ? props.datasetColor.rgba
                    : Highcharts.color(Highcharts.getOptions().colors[0])
                        .setOpacity(0)
                        .get("rgba"),
                ],
              ],
            },
          },
        },
        xAxis: {
          categories: props.data.map((v) => {
            return Highcharts.dateFormat(
              //   "%Y-%m-%d
              "%H:%M:%S.%L",
              new Date(v.xVal).getTime()
            );
          }),
        },
        series: {
          name: props.datasetName ? props.datasetName : "",
          data: props.data.map((v) => {
            return v.yVal;
          }),
        },
      });
    };
    updateChartOptions();
  }, [props.data, props.datasetColor, props.datasetName]);

  return (
    <div className="w-full h-full p-6">
      <HighchartsReact highcharts={Highcharts} options={chartOptions} />
    </div>
  );
}

export default LiveChart;
