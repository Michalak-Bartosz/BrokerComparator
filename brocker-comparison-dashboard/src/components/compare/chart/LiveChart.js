import React, { useEffect, useState } from "react";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";
import SwitchChartMode from "./SwitchChartMode";

function LiveChart(props) {
  const [isTimestampMode, setIsTimestampMode] = useState(true);

  const [chartOptions, setChartOption] = useState({
    chart: {
      backgroundColor: "rgba(255,255,255, 0.6)",
      borderWidth: 2,
      borderColor: "rgb(71 85 105)",
      borderRadius: "10",
      type: "area",
      height: props.heightPercentage ? props.heightPercentage : null,
      width: null,
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
        color: Highcharts.getOptions().colors[0],
        fillColor: {
          linearGradient: [0, 0, 0, 200],
          stops: [
            [0, Highcharts.getOptions().colors[0]],
            [
              1,
              Highcharts.color(Highcharts.getOptions().colors[0])
                .setOpacity(0)
                .get("rgba"),
            ],
          ],
        },
      },
    },
    xAxis: {
      type: "datetime",
      tickInterval: 1,
      visible: true,
      title: {
        text: props.xAxisName ? props.xAxisName : "Timestamp",
        style: {
          fontSize: "18px",
        },
      },
      categories: "",
    },
    yAxis: {
      title: {
        text: props.yAxisName ? props.yAxisName : "Values",
        style: {
          fontSize: "18px",
        },
      },
    },
    series: {
      name: "",
      data: "",
    },
  });

  useEffect(() => {
    const getSeriesColors = (data) => {
      return {
        color: data.datasetColor
          ? data.datasetColor.rgb
          : Highcharts.getOptions().colors[0],
        fillColor: {
          linearGradient: [0, 0, 0, 200],
          stops: [
            [
              0,
              data.datasetColor
                ? data.datasetColor.rgb
                : Highcharts.getOptions().colors[0],
            ],
            [
              1,
              data.datasetColor
                ? data.datasetColor.rgba
                : Highcharts.color(Highcharts.getOptions().colors[0])
                    .setOpacity(0)
                    .get("rgba"),
            ],
          ],
        },
      };
    };

    const getCategories = (data) => {
      if (isTimestampMode) {
        return data.dataArray
          ? data.dataArray.map((value, index) => {
              return Highcharts.dateFormat(
                //   "%Y-%m-%d
                "%H:%M:%S.%L",
                new Date(value.xVal).getTime()
              );
            })
          : "";
      } else {
        return null;
      }
    };

    const calculateSeriesData = () => {
      let maxIndex = 0;
      return props.chartData.map((data) => {
        if (data) {
          const seriesData = getSeriesData(maxIndex, data);
          maxIndex = maxIndex + data.dataArray.length;
          return seriesData;
        } else {
          return "";
        }
      });
    };

    const getSeriesData = (maxIndex, data) => {
      let datasetName = data.datasetName ? data.datasetName : "No data";
      let seriesData;
      if (data.dataArray) {
        seriesData = data.dataArray.map((value, currentIndex) => {
          let categoryIndex = currentIndex;
          if (isTimestampMode) {
            categoryIndex = categoryIndex + maxIndex;
          }
          return [categoryIndex, value.yVal];
        });
      } else {
        seriesData = "";
      }

      return {
        name: datasetName,
        data: seriesData,
      };
    };

    const updatePlotOptions = () => {
      return {
        plotOptions: {
          series: props.chartData.map((data) =>
            data ? getSeriesColors(data) : ""
          ),
        },
        xAxis: {
          categories: props.chartData
            .map((data) => getCategories(data))
            .flat(1),
        },
        series: calculateSeriesData(),
      };
    };

    if (props.chartData) {
      setChartOption(updatePlotOptions());
    }
  }, [props.chartData, isTimestampMode]);

  return (
    <div className="block p-6 w-full">
      <HighchartsReact highcharts={Highcharts} options={chartOptions} />
      <SwitchChartMode
        isTimestampMode={isTimestampMode}
        setIsTimestampMode={setIsTimestampMode}
      />
    </div>
  );
}

export default LiveChart;
