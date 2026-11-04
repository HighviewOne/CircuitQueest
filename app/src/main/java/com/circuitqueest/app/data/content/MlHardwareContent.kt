package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick

object MlHardwareContent {
    private val parameterBillions = listOf(1.0, 7.0, 13.0, 70.0)
    private val precisions = listOf("FP32" to 4.0, "FP16" to 2.0, "INT8" to 1.0, "INT4" to 0.5)
    private val coreCounts = listOf(5000.0, 10_000.0, 16_384.0)
    private val coreClocks = listOf(1.0, 1.5, 2.0)
    private val npuTops = listOf(10.0, 20.0, 40.0, 100.0)
    private val npuWatts = listOf(2.0, 5.0, 8.0, 15.0)
    private val memoryBandwidths = listOf(100.0, 400.0, 1000.0, 3000.0)
    private val modelSizes = listOf(7.0, 14.0, 70.0, 140.0)
    private val layerInputs = listOf(128, 512, 1024, 4096)
    private val layerOutputs = listOf(128, 256, 1000)
    private val formatBits = listOf("FP32" to 32, "FP16" to 16, "INT8" to 8, "INT4" to 4)

    val topic = Topic(
        id = "ml_hardware",
        title = "Machine Learning Hardware",
        subtitle = "GPUs, TPUs, and neural network accelerators",
        icon = "\uD83E\uDDE0",
        order = 17,
        lesson = Lesson(
            title = "Machine Learning Hardware: Silicon for Intelligence",
            sections = listOf(
                LessonSection(
                    heading = "Why Special Hardware for ML?",
                    content = "Machine learning workloads are dominated by matrix multiplications and convolutions \u2014 massively parallel operations on large data arrays.\n\nA typical neural network inference involves billions of multiply-accumulate (MAC) operations. A CPU executes these sequentially and slowly. Specialized hardware exploits parallelism:\n\n\u2022 CPU: general purpose, 10\u2013100 GFLOPS, flexible but slow for ML\n\u2022 GPU: thousands of cores, 10\u2013100+ TFLOPS, excellent for training\n\u2022 TPU/NPU: purpose-built for tensor operations, 100+ TOPS (int8)\n\u2022 FPGA: reconfigurable logic, good for custom architectures and low latency\n\nThe key metric is TOPS (Tera Operations Per Second), especially for integer (INT8) operations used in inference.",
                    keyPoint = "ML = massive parallelism; GPUs/TPUs exploit this with thousands of simple cores"
                ),
                LessonSection(
                    heading = "GPU Architecture for ML",
                    content = "GPUs were originally designed for graphics but their massively parallel architecture is ideal for ML:\n\n\u2022 Thousands of simple cores (CUDA cores in NVIDIA) execute the same operation on different data (SIMD/SIMT)\n\u2022 High memory bandwidth (1\u20133 TB/s with HBM) feeds data to cores\n\u2022 Tensor Cores: specialized units that compute 4\u00D74 matrix multiplies in one clock cycle\n\u2022 Mixed precision: FP16/BF16 training with FP32 accumulation doubles throughput\n\nA modern data center GPU (e.g., NVIDIA H100) has:\n\u2022 ~16,000 CUDA cores + 528 Tensor Cores\n\u2022 80 GB HBM3 at 3.35 TB/s\n\u2022 ~2000 TFLOPS (FP8 Tensor)\n\u2022 700W TDP\n\nThe memory bandwidth bottleneck (not compute) is often the limiting factor for large language models.",
                    formula = "FLOPS = cores \u00D7 ops/cycle \u00D7 clock\nMemory BW often limits performance",
                    keyPoint = "Tensor Cores for matrix math + HBM for bandwidth = modern ML GPU"
                ),
                LessonSection(
                    heading = "TPUs and Custom Accelerators",
                    content = "Google's Tensor Processing Unit (TPU) is designed specifically for neural network computation:\n\n\u2022 Systolic array architecture: data flows through a grid of multiply-accumulate units, maximizing data reuse and minimizing memory access\n\u2022 Optimized for matrix multiplication (the core ML operation)\n\u2022 Lower precision (INT8, BF16) for higher throughput\n\nOther custom accelerators:\n\u2022 Apple Neural Engine: 16-core NPU in M-series chips, ~35 TOPS\n\u2022 Qualcomm Hexagon NPU: in Snapdragon processors for on-device AI\n\u2022 Intel Gaudi: training accelerator with built-in networking\n\nThe systolic array approach trades flexibility for efficiency: fewer memory accesses per operation means higher TOPS/watt.",
                    formula = "Systolic array: N\u00D7N grid\nThroughput: N\u00B2 MACs/cycle",
                    keyPoint = "Systolic arrays maximize data reuse; TPUs trade flexibility for efficiency"
                ),
                LessonSection(
                    heading = "Quantization and Reduced Precision",
                    content = "Neural networks are surprisingly tolerant of lower precision arithmetic:\n\n\u2022 FP32 (32-bit float): full precision training, baseline accuracy\n\u2022 FP16/BF16 (16-bit): mixed-precision training, 2\u00D7 throughput, minimal accuracy loss\n\u2022 INT8 (8-bit integer): inference, 4\u00D7 throughput vs. FP32, <1% accuracy loss\n\u2022 INT4/INT2: aggressive quantization, 8\u201316\u00D7 throughput, some accuracy trade-off\n\nQuantization maps floating-point weights and activations to lower-precision integers:\nq = round(x / scale) + zero_point\n\nThis dramatically reduces memory, bandwidth, and compute requirements. A model quantized from FP32 to INT8 is 4\u00D7 smaller and runs 2\u20134\u00D7 faster with minimal quality loss.",
                    formula = "INT8: 4\u00D7 smaller, 2\u20134\u00D7 faster\nq = round(x/scale) + zero_point",
                    keyPoint = "INT8 quantization: 4\u00D7 smaller model, 2\u20134\u00D7 faster, <1% accuracy loss"
                ),
                LessonSection(
                    heading = "Memory and Interconnect Challenges",
                    content = "Large ML models are often memory-bound, not compute-bound:\n\n\u2022 A large language model can have 70\u2013400+ billion parameters\n\u2022 At FP16, 70B parameters = 140 GB \u2014 exceeds single GPU memory\n\u2022 Solution: model parallelism across multiple GPUs\n\nHigh Bandwidth Memory (HBM) stacks DRAM vertically:\n\u2022 HBM3: 3+ TB/s bandwidth per stack\n\u2022 Connected via silicon interposer (2.5D packaging)\n\nMulti-GPU interconnects:\n\u2022 NVLink: 900 GB/s between GPUs (much faster than PCIe)\n\u2022 InfiniBand: 400 Gb/s between nodes in a cluster\n\nThe arithmetic intensity (ops per byte of memory accessed) determines whether a workload is compute-bound or memory-bound.",
                    formula = "Model size: params \u00D7 bytes/param\nArithmetic intensity = FLOPs / bytes accessed",
                    keyPoint = "Large models need HBM bandwidth and multi-GPU parallelism; memory is the bottleneck"
                ),
                LessonSection(
                    heading = "Edge AI and On-Device Inference",
                    content = "Running ML inference on edge devices (phones, cameras, IoT) has strict constraints:\n\n\u2022 Power: milliwatts to a few watts (battery-powered)\n\u2022 Latency: real-time (<10 ms for vision, <50 ms for voice)\n\u2022 Size: must fit in tiny packages\n\nEdge AI hardware:\n\u2022 Smartphone NPUs: 10\u201345 TOPS at 5\u201310W\n\u2022 Microcontroller ML: TinyML runs small models on Cortex-M MCUs (<1 mW)\n\u2022 Vision processing units (VPUs): optimized for image/video inference\n\nKey optimization techniques:\n\u2022 Model pruning: remove unnecessary weights (50\u201390% sparsity)\n\u2022 Knowledge distillation: train a small model to mimic a large one\n\u2022 Quantization: INT8 or INT4 for minimal memory and compute\n\nThe goal: TOPS/watt \u2014 maximum performance per unit of energy.",
                    formula = "Efficiency metric: TOPS/watt\nTinyML: <1 mW, <1 MB models",
                    keyPoint = "Edge AI: TOPS/watt is king; pruning + quantization make models tiny and fast"
                )
            )
        ),
        quiz = Quiz(
            title = "Machine Learning Hardware Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "mlhw_mc1",
                    questionText = "The dominant operation in neural network computation is:",
                    options = listOf(
                        "Sorting",
                        "Matrix multiplication (multiply-accumulate)",
                        "String matching",
                        "Graph traversal"
                    ),
                    correctIndex = 1,
                    explanation = "Neural networks are dominated by matrix multiplications and convolutions, both of which reduce to massive numbers of multiply-accumulate (MAC) operations."
                ),
                Question.MultipleChoice(
                    id = "mlhw_mc2",
                    questionText = "A TPU's systolic array architecture is efficient because it:",
                    options = listOf(
                        "Uses the highest clock frequency",
                        "Maximizes data reuse, minimizing memory accesses",
                        "Has the largest cache",
                        "Runs standard x86 instructions"
                    ),
                    correctIndex = 1,
                    explanation = "In a systolic array, data flows through a grid of processing elements, being reused at each step. This minimizes expensive memory accesses and maximizes compute per byte."
                ),
                Question.MultipleChoice(
                    id = "mlhw_mc3",
                    questionText = "INT8 quantization reduces model size compared to FP32 by:",
                    options = listOf(
                        "2\u00D7",
                        "4\u00D7",
                        "8\u00D7",
                        "16\u00D7"
                    ),
                    correctIndex = 1,
                    explanation = "FP32 uses 4 bytes per value, INT8 uses 1 byte. The model is 4\u00D7 smaller (32/8 = 4)."
                ),
                Question.MultipleChoice(
                    id = "mlhw_mc4",
                    questionText = "For large language models, the primary performance bottleneck is often:",
                    options = listOf(
                        "CPU clock speed",
                        "Disk storage capacity",
                        "Memory bandwidth",
                        "Network latency"
                    ),
                    correctIndex = 2,
                    explanation = "Large language models must load billions of parameters from memory for each token. Memory bandwidth (not compute) is typically the limiting factor, which is why HBM is critical."
                ),
                Question.MultipleChoice(
                    id = "mlhw_mc5",
                    questionText = "Why is INT8 inference usually faster and more energy-efficient than FP32?",
                    options = listOf(
                        "INT8 is more accurate",
                        "Integer multipliers are smaller, and each value moves a quarter of the data",
                        "It runs at a higher clock speed",
                        "It needs no memory"
                    ),
                    correctIndex = 1,
                    explanation = "An 8-bit integer multiplier is far smaller and cheaper in energy than a 32-bit float unit, and each value is 1 byte instead of 4, so memory traffic (often the bottleneck) drops 4×."
                )
            ),
            generators = listOf(
                generator("mlhw_ni1") { r ->
                    val b = parameterBillions.pick(r)
                    val (format, bytes) = precisions.pick(r)
                    val gb = b * bytes
                    val byteWord = if (bytes == 1.0) "byte" else "bytes"
                    Numeric(
                        text = "A model has ${fmt(b)} billion parameters stored in $format (${fmt(bytes)} $byteWord each). " +
                            "How many GB of memory do the weights need?",
                        answer = gb, unit = "GB",
                        explanation = "Memory = ${fmt(b)}×10⁹ × ${fmt(bytes)} $byteWord = ${fmt(gb)}GB"
                    )
                },
                generator("mlhw_ni2") { r ->
                    val cores = coreCounts.pick(r)
                    val ghz = coreClocks.pick(r)
                    val tflops = cores * ghz * 2 / 1000
                    Numeric(
                        text = "A GPU has ${fmt(cores)} cores at ${fmt(ghz)}GHz, each doing one fused multiply-add (2 operations) " +
                            "per cycle. What is its peak throughput in TFLOPS? Round to 2 decimal places.",
                        answer = tflops, unit = "TFLOPS",
                        explanation = "${fmt(cores)} × ${fmt(ghz)}×10⁹ × 2 = ${fmt(tflops, 2)}×10¹² FLOPS = ${fmt(tflops, 2)}TFLOPS"
                    )
                },
                generator("mlhw_ni3") { r ->
                    val tops = npuTops.pick(r)
                    val w = npuWatts.pick(r)
                    val eff = tops / w
                    Numeric(
                        text = "An NPU delivers ${fmt(tops)} TOPS at ${fmt(w)}W. What is its efficiency in TOPS/W? " +
                            "Round to 2 decimal places.",
                        answer = eff, unit = "TOPS/W",
                        explanation = "Efficiency = ${fmt(tops)} TOPS/${fmt(w)}W = ${fmt(eff, 2)}TOPS/W"
                    )
                },
                generator("mlhw_ni4") { r ->
                    val bw = memoryBandwidths.pick(r)
                    val size = modelSizes.pick(r)
                    val tps = bw / size
                    Numeric(
                        text = "A language model's weights take ${fmt(size)}GB and the accelerator has ${fmt(bw)}GB/s of memory " +
                            "bandwidth. If each generated token must read all the weights once, roughly how many tokens per " +
                            "second can it produce? Round to 2 decimal places.",
                        answer = tps, unit = "tokens/s",
                        explanation = "Memory-bound: tokens/s ≈ bandwidth/model size = ${fmt(bw)}/${fmt(size)} = ${fmt(tps, 2)}tokens/s"
                    )
                },
                generator("mlhw_ni5") { r ->
                    val inputs = layerInputs.pick(r)
                    val outputs = layerOutputs.pick(r)
                    val macs = inputs.toLong() * outputs
                    Numeric(
                        text = "A fully connected layer has $inputs inputs and $outputs outputs. How many multiply-accumulate " +
                            "operations (MACs) does one forward pass take (ignore the bias)?",
                        answer = macs.toDouble(), unit = "MACs", exact = true,
                        explanation = "Each of the $outputs outputs is a dot product over $inputs inputs: $inputs × $outputs = $macs MACs"
                    )
                },
                generator("mlhw_ni6") { r ->
                    val (from, fromBits) = formatBits.take(2).pick(r)
                    val (to, toBits) = formatBits.drop(2).pick(r)
                    val ratio = fromBits / toBits
                    Numeric(
                        text = "A model is quantized from $from to $to. How many times smaller do its weights become?",
                        answer = ratio.toDouble(), unit = "×", exact = true,
                        explanation = "$from uses $fromBits bits per value and $to uses $toBits: $fromBits/$toBits = $ratio×"
                    )
                }
            )
        )
    )
}
