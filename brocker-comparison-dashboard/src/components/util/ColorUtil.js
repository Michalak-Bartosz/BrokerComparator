export const randomDatasetColor = () => {
  let r = Math.floor(Math.random() * 256); // Random between 0-255
  let g = Math.floor(Math.random() * 256); // Random between 0-255
  let b = Math.floor(Math.random() * 256); // Random between 0-255
  return {
    rgb: "rgb(" + r + "," + g + "," + b + ")",
    rgba: "rgba(" + r + "," + g + "," + b + "," + 0.5 + ")",
  };
};
