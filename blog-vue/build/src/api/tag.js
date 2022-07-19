import request from "@/request";

export function getAllTags() {
  return request({
    url: "/articles/allTags",
    method: "get"
  });
}

export function getAllTagsDetail() {
  return request({
    url: "/articles/tagsDetail",
    method: "get"
  });
}

export function getHotTags() {
  return request({
    url: "/articles/hotTags",
    method: "get"
  });
}

export function getTag(id) {
  return request({
    url: `/tags/${id}`,
    method: "get"
  });
}

export function getTagDetail(id) {
  return request({
    url: `/articles/tagDetail/${id}`,
    method: "get"
  });
}
