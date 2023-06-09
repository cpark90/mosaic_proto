#WORKSPACE
workspace(name = "mosaic_proto")

load("//bazel_scripts:mosaic_proto_rules.bzl", "common_proto_rules", "test_rules", "java_proto_rules", "python_proto_rules")
test_rules()
common_proto_rules()
java_proto_rules()
python_proto_rules()


# python
load("@rules_python//python:repositories.bzl", "py_repositories")
py_repositories()

load("@rules_python//python:pip.bzl", "pip_parse")
pip_parse(
    name = "python_deps",
    requirements_lock = "//third_party:requirements.txt",
)

load("@python_deps//:requirements.bzl", "install_deps")
install_deps()


# protocol buffer
load("@rules_proto//proto:repositories.bzl", "rules_proto_dependencies", "rules_proto_toolchains")
rules_proto_dependencies()
rules_proto_toolchains()


# GRPC
load("@com_github_grpc_grpc//bazel:grpc_deps.bzl", "grpc_deps")
grpc_deps()

load("@com_github_grpc_grpc//bazel:grpc_extra_deps.bzl", "grpc_extra_deps")
grpc_extra_deps()

load("@com_github_grpc_grpc//bazel:grpc_python_deps.bzl", "grpc_python_deps")
grpc_python_deps()


# Download the rules_docker repository at release v0.14.4
load(
    "@io_bazel_rules_docker//repositories:repositories.bzl",
    container_repositories = "repositories",
)
container_repositories()

load("@io_bazel_rules_docker//repositories:deps.bzl", container_deps = "deps")

container_deps()

load(
    "@io_bazel_rules_docker//container:container.bzl",
    "container_pull",
)

load(
    "@io_bazel_rules_docker//python:image.bzl",
    _py_image_repos = "repositories",
)
_py_image_repos()

load(
    "@io_bazel_rules_docker//java:image.bzl",
    _java_image_repos = "repositories",
)

_java_image_repos()



# java container image
container_pull(
    name = "java-base-image",
    registry = "docker.io",
    repository = "cpark90/openjdk11",
    tag = "jre-11.0.19_7-ubuntu-docker",
)


# python container
container_pull(
    name = "python-base-image",
    registry = "docker.io",
    repository = "python",
    tag = "3.7.16",
)
