import { format } from "date-fns";
import moment from "moment";

const formatTime = "HH:mm:ss.SSS";

export function getDateFromTimestampString(timestampString) {
  return new Date(timestampString);
}

export function getFormatedTimeString(timestampString) {
  return format(new Date(timestampString), formatTime);
}

export function getDurationInMilliseconds(duration) {
  return parseFloat(moment.duration(duration).as("milliseconds").toFixed(3));
}
