import React, { useEffect, useState } from "react";
import { storage } from "../../../config/firebase";
import { useParams } from "react-router-dom";
import "./product-Innovation.css";
import ProductImages from "./ProductImages";
import PopUp from "../pop-up-component/PopUpComponent";
import { ProductService } from "../../../misc/ProductService";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const AddNewProduct = () => {
  const [categories, setCategories] = useState([]);
  const [productTypes, setProductTypes] = useState([]);

  const [productImageList, setProductImageList] = useState([]);
  const { itemId } = useParams();

  const title = itemId ? "CẬP NHẬT SẢN PHẨM" : "THÊM MỚI SẢN PHẨM";
  const buttonLabel = itemId ? "CẬP NHẬT" : "THÊM MỚI";

  const handleCalculate = (retailPrice, discountPercent) => {
    const salePrice = retailPrice - (retailPrice * discountPercent) / 100;
    const result = Math.floor(salePrice / 100) * 100;
    return result;
  };

  useEffect(() => {
    ProductService.getAllCategories()
      .then((res) => {
        setCategories(res.data);
      })
      .catch((err1) => console.error);
    ProductService.getAllProductTypes()
      .then((res) => {
        setProductTypes(res.data);
      })
      .catch((err2) => console.error);
  }, []);

  useEffect(() => {
    if (itemId) {
      ProductService.getProductById(itemId)
        .then((res) => {
          console.log("response", res);
          setFormData(res.data);
          const images = res.data.productImages;
          let newImageList = images.map((obj) => {
            return { status: "FINISH", downloadURL: obj.imageURL };
          });
          setProductImageList(newImageList);
        })
        .catch((err1) => console.error);
    }
  }, [itemId]);

  useEffect(() => {
    let newImageList = productImageList.map((obj) => {
      return { imageId: null, imageURL: obj.downloadURL };
    });
    setFormData({
      ...formData,
      productImages: newImageList,
    });
  }, [productImageList]);

  const [formData, setFormData] = useState({
    id: null,
    code: "",
    name: "",
    unit: "",
    title: "",
    avatarURL: "",
    information: "",
    description: "",
    note: "",
    weight: null,
    quantity: null,
    costPrice: null,
    wholesalePrice: null,
    retailPrice: null,
    discountPercent: null,
    salePrice: null,
    reviewScore: null,
    category: { categoryId: null, categoryCode: "", categoryName: "" },
    productType: { productTypeId: null, productTypeName: "" },
    productImages: productImageList,
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (name === "category") {
      // Tìm đối tượng category tương ứng với giá trị được chọn
      const selectedCategory = categories.find(
        (category) => category.categoryId == value
      );
      genProductCode(selectedCategory.categoryCode);
      setFormData({
        ...formData,
        category: selectedCategory,
      });
    } else if (name === "productType") {
      // Tìm đối tượng productType tương ứng với giá trị được chọn
      const selectedProductType = productTypes.find(
        (productType) => productType.productTypeId == value
      );
      setFormData({
        ...formData,
        productType: selectedProductType,
      });
    } else if (name === "file") {
      if (!e.target.files || e.target.files.length === 0) {
        return;
      }
      const imageFile = e.target.files[0];
      uploadFiles(imageFile);
    } else {
      setFormData({
        ...formData,
        [name]: value,
      });
    }
  };

  useEffect(() => {
    setFormData({
      ...formData,
      salePrice: handleCalculate(
        formData.retailPrice,
        formData.discountPercent
      ),
    });
  }, [formData.retailPrice, formData.discountPercent]);

  const genProductCode = (categoryCode) => {
    const randomNumber = Math.floor(Math.random() * 90000) + 10000;
    const generateCode = categoryCode + randomNumber;
    setFormData({
      ...formData,
      code: generateCode,
    });
  };

  const uploadFiles = (file) => {
    const uploadTask = storage.ref(`files/${file.name}`).put(file);
    uploadTask.on(
      "state_changed",
      (snapshot) => {
        const prog = Math.round(
          (snapshot.bytesTransferred / snapshot.totalBytes) * 100
        );
        console.log("progress", prog);
      },
      (error) => console.log(error),
      () => {
        storage
          .ref("files")
          .child(file.name)
          .getDownloadURL()
          .then((url) => {
            setFormData({
              ...formData,
              avatarURL: url,
            });
          });
      }
    );
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("handleSubmit formData", formData);
    ProductService.createProduct(formData);
    // .then()
    // .catch((err) => toast.error("CREATE FAILED!"));
  };

  const [popupMessage, setPopupMessage] = useState("");
  const [showPopup, setShowPopup] = useState(false);

  return (
    <div className="add-product">
      <div className="add-product-form">
        <form onSubmit={handleSubmit}>
          <div className="title row">
            <div className="col-8">
              <h2>{title}</h2>
            </div>
            <div className="col-4 add-product-button">
              <button type="submit">{buttonLabel}</button>
            </div>
          </div>
          <div className="row">
            <div className="col-lg-2 col-md-12 col-12 center">
              <div className="form-group-avatar-upload">
                <input
                  type="file"
                  name="file"
                  accept="image/*"
                  onChange={handleInputChange}
                />
                {formData.avatarURL && (
                  <img src={formData.avatarURL} alt="product" />
                )}
                {!formData.avatarURL ? (
                  <>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="100"
                      height="100"
                      viewBox="0 0 50 50"
                      fill="none"
                    >
                      <path
                        d="M20.8333 33.3333H29.1666C30.3125 33.3333 31.25 32.3958 31.25 31.25V20.8333H34.5625C36.4166 20.8333 37.3541 18.5833 36.0416 17.2708L26.4791 7.70832C26.2864 7.51519 26.0575 7.36196 25.8054 7.25742C25.5534 7.15287 25.2832 7.09906 25.0104 7.09906C24.7375 7.09906 24.4674 7.15287 24.2153 7.25742C23.9633 7.36196 23.7344 7.51519 23.5416 7.70832L13.9791 17.2708C12.6666 18.5833 13.5833 20.8333 15.4375 20.8333H18.75V31.25C18.75 32.3958 19.6875 33.3333 20.8333 33.3333ZM12.5 37.5H37.5C38.6458 37.5 39.5833 38.4375 39.5833 39.5833C39.5833 40.7291 38.6458 41.6666 37.5 41.6666H12.5C11.3541 41.6666 10.4166 40.7291 10.4166 39.5833C10.4166 38.4375 11.3541 37.5 12.5 37.5Z"
                        fill="#c9c8c8"
                      />
                    </svg>
                    <p className="upload-avatar-text">TẢI ẢNH HIỂN THỊ</p>
                  </>
                ) : null}
              </div>
            </div>
            <div className="col-lg-10 col-12 p-0">
              <div className="row justify-content-around">
                <div className="col-lg-3 col-sm-6 col-6 row">
                  <label>
                    Danh Mục
                    <select
                      name="category"
                      id="category"
                      value={formData.category.categoryId}
                      onChange={handleInputChange}
                      className="form-control "
                    >
                      <option value="0">-- Chọn danh mục--</option>
                      {categories.map((item) => (
                        <option key={item.categoryId} value={item.categoryId}>
                          {item.categoryName}
                        </option>
                      ))}
                    </select>
                  </label>
                </div>

                <div className="col-lg-3 col-sm-6 col-6 row">
                  <label>
                    Dòng Sản Phẩm
                    <select
                      name="productType"
                      id="productType"
                      value={formData.productType.productTypeId}
                      onChange={handleInputChange}
                      className="form-control "
                    >
                      <option value="0">-- Chọn dòng sản phẩm--</option>
                      {productTypes.map((item) => (
                        <option
                          key={item.productTypeId}
                          value={item.productTypeId}
                        >
                          {item.productTypeName}
                        </option>
                      ))}
                    </select>
                  </label>
                </div>

                <div className="col-lg-4 col-sm-8 row">
                  <label>
                    Tên Sản Phẩm
                    <input
                      type="text"
                      name="name"
                      value={formData.name}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>

                <div className="col-lg-2 col-sm-4 row">
                  <label>
                    Mã Sản Phẩm
                    <input
                      type="text"
                      name="code"
                      value={formData.code}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>
              </div>

              <div className="row justify-content-around">
                <div className="col-lg-3 col-sm-6 row">
                  <label>
                    Khối Lượng (gram)
                    <input
                      type="number"
                      name="weight"
                      value={formData.weight}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>

                <div className="col-lg-3 col-sm-6 row">
                  <label>
                    Số Lượng Trong Kho
                    <input
                      type="number"
                      name="quantity"
                      value={formData.quantity}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>

                <div className="col-lg-3 col-sm-6 row">
                  <label>
                    Đơn Vị Tính
                    <input
                      type="text"
                      name="unit"
                      value={formData.unit}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>

                <div className="col-lg-3 col-sm-6 row">
                  <label>
                    Giá Vốn
                    <input
                      type="number"
                      name="costPrice"
                      value={formData.costPrice}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>
              </div>

              <div className="row justify-content-around">
                <div className="col-lg-3 col-sm-6 row">
                  <label>
                    Giá Bán Sỉ
                    <input
                      type="number"
                      name="wholesalePrice"
                      value={formData.wholesalePrice}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>

                <div className="col-lg-3 col-sm-6 row">
                  <label>
                    Giá Bán Lẻ
                    <input
                      type="number"
                      name="retailPrice"
                      value={formData.retailPrice}
                      className="form-control "
                    />
                  </label>
                </div>

                <div className="col-lg-3 col-sm-6 row">
                  <label>
                    % Chiết Khấu
                    <input
                      type="number"
                      name="discountPercent"
                      value={formData.discountPercent}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>

                <div className="col-lg-3 col-sm-6 row">
                  <label>
                    Giá Khuyến Mãi
                    <input
                      disabled={true}
                      type="number"
                      name="salePrice"
                      value={formData.salePrice}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>
              </div>
            </div>
          </div>

          <div className="row mt-3">
            <div className="col-lg-8 col-12">
              <div className="row">
                <div className="col-12">
                  <div className="row">
                    <label>
                      Mô Tả Ngắn
                      <input
                        type="text"
                        name="title"
                        value={formData.title}
                        onChange={handleInputChange}
                        className="form-control"
                      />
                    </label>
                  </div>
                </div>
              </div>
              <div>
                <div className="row">
                  <label>
                    Thêm Thông Tin
                    <textarea
                      name="information"
                      value={formData.information}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>
              </div>

              <div>
                <div className="row">
                  <label>
                    Thêm Mô Tả
                    <textarea
                      name="description"
                      value={formData.description}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>
              </div>

              <div>
                <div className="row">
                  <label>
                    Thêm Ghi Chú
                    <textarea
                      name="note"
                      value={formData.note}
                      onChange={handleInputChange}
                      className="form-control "
                    />
                  </label>
                </div>
              </div>
            </div>
            <div className="col-lg-4 col-12">
              <div className="muti-images-upload-group">
                <div className="">
                  <ProductImages
                    imageList={productImageList}
                    setImageList={setProductImageList}
                  />
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
      <PopUp
        message={popupMessage}
        show={showPopup}
        onClose={() => setShowPopup(false)}
      />
    </div>
  );
};

export default AddNewProduct;
