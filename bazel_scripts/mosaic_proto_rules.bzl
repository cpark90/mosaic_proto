load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")


def common_proto_rules():
    """Loads common dependencies needed to compile the protobuf library."""
    if not native.existing_rule("rules_proto"):
        http_archive(
            name = "rules_proto",
            sha256 = "dc3fb206a2cb3441b485eb1e423165b231235a1ea9b031b4433cf7bc1fa460dd",
            strip_prefix = "rules_proto-5.3.0-21.7",
            urls = [
                "https://github.com/bazelbuild/rules_proto/archive/refs/tags/5.3.0-21.7.tar.gz",
            ],
        )
    if not native.existing_rule("com_github_grpc_grpc"):
        git_repository(
            name = "com_github_grpc_grpc",
            remote = "https://github.com/grpc/grpc",
            tag = "v1.45.2",
        )

def java_proto_rules():
    if not native.existing_rule("rules_java"):
        git_repository(
            name = "rules_java",
            remote = "https://github.com/bazelbuild/rules_java.git",
            tag = "6.1.0",
        )
 
def python_proto_rules():
    pass

def test_rules():
    if not native.existing_rule("rules_python"):
        git_repository(
            name = "rules_python",
            remote = "https://github.com/bazelbuild/rules_python.git",
            tag = "0.22.0",
        )

    if not native.existing_rule("io_bazel_rules_docker"):
        http_archive(
            name = "io_bazel_rules_docker",
            sha256 = "b1e80761a8a8243d03ebca8845e9cc1ba6c82ce7c5179ce2b295cd36f7e394bf",
            urls = ["https://github.com/bazelbuild/rules_docker/releases/download/v0.25.0/rules_docker-v0.25.0.tar.gz"],
        )
    