import React, { useEffect, useRef } from "react";
import * as d3 from "d3";

interface EventGraphProps {
  events: any[];
}

const EventGraph = ({ events }: EventGraphProps) => {
  const d3Container = useRef(null);

  useEffect(() => {
    if (events.length > 0) {
      // Set up the SVG container
      const svg = d3
        .select(d3Container.current)
        .attr("width", 600)
        .attr("height", 400)
        .style("background-color", "#f0f0f0");

      // Clear the previous rendering
      svg.selectAll("*").remove();

      // Set up margins
      const margin = { top: 20, right: 30, bottom: 40, left: 40 },
        width = 600 - margin.left - margin.right,
        height = 400 - margin.top - margin.bottom;

      const chart = svg
        .append("g")
        .attr("transform", `translate(${margin.left},${margin.top})`);

      // Set up scales
      const x = d3
        .scaleBand()
        .domain(events.map((event: any) => event.name))
        .range([0, width])
        .padding(0.1);

      const y = d3
        .scaleLinear()
        .domain([0, d3.max(events, (event: any) => event.attendees.length)])
        .nice()
        .range([height, 0]);

      // Add the x-axis
      chart
        .append("g")
        .attr("transform", `translate(0,${height})`)
        .call(d3.axisBottom(x))
        .selectAll("text")
        .attr("transform", "rotate(-40)")
        .style("text-anchor", "end");

      // Add the y-axis
      chart.append("g").call(d3.axisLeft(y));

      // Add the bars
      chart
        .selectAll(".bar")
        .data(events)
        .enter()
        .append("rect")
        .attr("class", "bar")
        .attr("x", (event: any) => x(event.name))
        .attr("y", (event: any) => y(event.attendees.length))
        .attr("width", x.bandwidth())
        .attr("height", (event) => height - y(event.attendees.length))
        .attr("fill", "steelblue");
    }
  }, [events]);

  return (
    <svg className="d3-component " width={600} height={400} ref={d3Container} />
  );
};

export default EventGraph;
