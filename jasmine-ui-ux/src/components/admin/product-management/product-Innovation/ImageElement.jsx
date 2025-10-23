import React from "react";
import "./ImageElement.css";

export default function ImageElement({
  imageURL,
  index,
  isFirstElement,
  isLastElement,
  handleChangeOrderUp,
  handleChangeOrderDown,
  handleDeleteImage,
  changeImageField,
}) {
  return (
    <div className="col-4">
      {imageURL ? (
        <div className="image-container mb-3">
          <img
            src={imageURL}
            alt={`Upload Preview ${index + 1}`}
            className="square-image"
          />
          <i
            className="fa-solid fa-xmark delete-button"
            onClick={() => handleDeleteImage(index)}
          />

          <i
            class="fa-solid fa-angle-left prev-button"
            onClick={() => handleChangeOrderUp(index)}
          ></i>

          <i
            class="fa-solid fa-angle-right next-button"
            onClick={() => handleChangeOrderDown(index)}
          ></i>
        </div>
      ) : (
        <div className="d-flex align-items-center">
          <i className="fa-solid fa-spinner" />
          <span className="text-primary"> Uploading...</span>
        </div>
      )}
    </div>
  );
}
