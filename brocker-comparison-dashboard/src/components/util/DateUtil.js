import { format } from "date-fns";

const formatDate = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
const formatTime = "HH:mm:ss.SSS";

export function getDateFromTimestampString(timestampString) {
  return new Date(timestampString);
}

export function getFormatedTimeString(timestampString) {
  return format(new Date(timestampString), formatTime);
}
