package com.droiddeck.launcher.core

import com.droiddeck.launcher.R

object GameEnvironmentOptions {
    enum class Type { TOGGLE, CHOICE, MULTIPLE, NUMBER, TEXT }
    data class Option(val name: String, val value: String, val detail: Int, val type: Type, val choices: List<String> = emptyList())

    val all = listOf(
        Option("MESA_SHADER_CACHE_DISABLE", "false", R.string.game_env_cache, Type.TOGGLE, listOf("false", "true")),
        Option("VKD3D_FEATURE_LEVEL", "12_2", R.string.game_env_feature, Type.CHOICE, listOf("12_2", "12_1", "12_0", "11_1", "11_0")),
        Option("VKD3D_SHADER_MODEL", "6_9", R.string.game_env_shader, Type.CHOICE, listOf("6_9", "6_8", "6_7", "6_6", "6_5", "6_4", "6_3", "6_2", "6_1", "6_0", "5_1")),
        Option("VKD3D_CONFIG", "nodxr", R.string.game_env_vkd3d_config, Type.MULTIPLE, listOf("nodxr", "single_queue", "no_upload_hvv")),
        Option("MESA_SHADER_CACHE_MAX_SIZE", "1G", R.string.game_env_cache_size, Type.CHOICE, listOf("512M", "1G", "2G", "4G")),
        Option("PROTON_LOG", "1", R.string.game_env_proton_log, Type.TOGGLE, listOf("0", "1")),
        Option("PROTON_USE_WINED3D", "1", R.string.game_env_wined3d, Type.TOGGLE, listOf("0", "1")),
        Option("PROTON_DXVK_D3D8", "1", R.string.game_env_dxvk_d3d8, Type.TOGGLE, listOf("0", "1")),
        Option("WINE_D3D_CONFIG", "renderer=vulkan", R.string.game_env_wined3d_renderer, Type.CHOICE, listOf("renderer=vulkan", "renderer=gl")),
        Option("PROTON_USE_XALIA", "0", R.string.game_env_xalia, Type.TOGGLE, listOf("0", "1")),
        Option("PROTON_NO_NTSYNC", "1", R.string.game_env_no_ntsync, Type.TOGGLE, listOf("0", "1")),
        Option("PROTON_NO_ESYNC", "1", R.string.game_env_no_esync, Type.TOGGLE, listOf("0", "1")),
        Option("PROTON_NO_FSYNC", "1", R.string.game_env_no_fsync, Type.TOGGLE, listOf("0", "1")),
        Option("DD_FSYNC_FIRST", "1", R.string.game_env_fsync_first, Type.TOGGLE, listOf("0", "1")),
        Option("DXVK_HUD", "fps", R.string.game_env_hud, Type.MULTIPLE, listOf("scale=0.5", "scale=0.7", "opacity=0.5", "opacity=0.7", "devinfo", "fps", "frametimes", "submissions", "drawcalls", "pipelines", "descriptors", "memory", "gpuload", "version", "api", "cs", "compiler", "samplers")),
        Option("DXVK_CONFIG", "dxvk.maxFrameRate = 60", R.string.game_env_dxvk_config, Type.TEXT),
        Option("VKD3D_FRAME_RATE", "60", R.string.game_env_vkd3d_limit, Type.NUMBER),
        Option("mesa_glthread", "true", R.string.game_env_glthread, Type.TOGGLE, listOf("false", "true")),
        Option("ZINK_DESCRIPTORS", "auto", 0, Type.CHOICE, listOf("auto", "lazy", "cached", "notemplates")),
        Option("ZINK_DEBUG", "nir", 0, Type.MULTIPLE, listOf("nir", "spirv", "tgsi", "validation", "sync", "compact", "noreorder")),
        Option("SteamDeck", "0", 0, Type.TOGGLE, listOf("0", "1")),
        Option("FD_DEV_FEATURES", "enable_tp_ubwc_flag_hint=1", 0, Type.MULTIPLE, listOf("enable_tp_ubwc_flag_hint=1", "storage_8bit=1")),
        Option("TU_DEBUG", "forcecb", 0, Type.MULTIPLE, listOf("forcecb", "nocb", "startup", "deck_emu", "nir", "nobin", "sysmem", "gmem", "forcebin", "layout", "noubwc", "nomultipos", "nolrz", "nolrzfc", "perf", "perfc", "flushall", "syncdraw", "push_consts_per_stage", "rast_order", "unaligned_store", "log_skip_gmem_ops", "dynamic", "bos", "3d_load", "fdm", "noconform", "rd")),
        Option("IR3_SHADER_DEBUG", "nouboopt", 0, Type.MULTIPLE, listOf("nouboopt", "nopreamble", "noearlypreamble")),
        Option("MESA_EXTENSION_MAX_YEAR", "", 0, Type.TEXT),
        Option("MESA_GL_VERSION_OVERRIDE", "", 0, Type.TEXT),
        Option("PULSE_LATENCY_MSEC", "", 0, Type.NUMBER),
        Option("AUDIO_DIRECT_MS", "", 0, Type.NUMBER),
        Option("AUDIO_DIRECT_PERIOD_MS", "", 0, Type.NUMBER),
        Option("DROIDDECK_GAME_CORES", "", R.string.game_env_cores, Type.TEXT),
        Option("DROIDDECK_ANTICHEAT_OFFLINE", "1", R.string.game_env_anticheat_offline, Type.TOGGLE, listOf("0", "1")),
        Option("WINE_LARGE_ADDRESS_AWARE", "0", 0, Type.TOGGLE, listOf("0", "1")),
        Option("WINEDLLOVERRIDES", "", 0, Type.TEXT),
        Option("GALLIUM_HUD", "simple", 0, Type.MULTIPLE, listOf("simple", "fps", "frametime")),
        // --- Baseline-aligned FEX group (WoWBox p11 profile) ---
        Option("FEX_MAXINST", "5000", 0, Type.NUMBER),
        Option("FEX_HOSTFEATURES", "off", 0, Type.CHOICE, listOf("off", "enablesve", "disablesve", "enableavx", "disableavx")),
        Option("FEX_SMALLTSCSCALE", "1", 0, Type.TOGGLE, listOf("0", "1")),
        Option("FEX_SMC_CHECKS", "mtrack", 0, Type.CHOICE, listOf("none", "mtrack", "full")),
        Option("FEX_VOLATILEMETADATA", "1", 0, Type.TOGGLE, listOf("0", "1")),
        Option("FEX_MONOHACKS", "1", 0, Type.TOGGLE, listOf("0", "1")),
        Option("FEX_HIDEHYPERVISORBIT", "0", 0, Type.TOGGLE, listOf("0", "1")),
        Option("FEX_BNET_DETECT_LOG", "0", 0, Type.TOGGLE, listOf("0", "1")),
        Option("FEX_DISABLEL2CACHE", "0", 0, Type.TOGGLE, listOf("0", "1")),
        Option("FEX_DYNAMICL1CACHE", "0", 0, Type.TOGGLE, listOf("0", "1")),
        Option("FEX_SILENTLOG", "1", 0, Type.TOGGLE, listOf("0", "1")),
        Option("FEX_OUTPUTLOG", "stderr", 0, Type.CHOICE, listOf("stderr", "stdout")),
        Option("FEX_BNET_ID_NTQUERYSYSTEMINFORMATION", "0x9A", 0, Type.TEXT),
        Option("FEX_BNET_ID_NTQUERYINFORMATIONPROCESS", "0x89", 0, Type.TEXT),
        Option("FEX_BNET_ID_NTQUERYINFORMATIONTHREAD", "0x8A", 0, Type.TEXT),
        // --- Graphics / driver / sync knobs ---
        Option("TU_PVTMEM_MIN_FIBER", "4608", 0, Type.NUMBER),
        Option("DXVK_FRAME_RATE", "30", 0, Type.NUMBER),
        Option("WINE_FDSAN_LEVEL", "off", 0, Type.CHOICE, listOf("off", "warn", "error", "strict")),
        Option("WINEESYNC", "0", 0, Type.TOGGLE, listOf("0", "1")),
        Option("WINEFSYNC", "0", 0, Type.TOGGLE, listOf("0", "1")),
        Option("HODLL", "libwow64fex.dll", 0, Type.TEXT),
        Option("DXVK_LOG_LEVEL", "info", 0, Type.CHOICE, listOf("none", "error", "warn", "info", "debug")),
        Option("VKD3D_DEBUG", "warn", 0, Type.CHOICE, listOf("none", "error", "warn", "info", "debug")),
        // --- Runtime injection hooks: extra command-line arguments ---
        Option("DD_BNET_FLAGS", "", 0, Type.TEXT),
        Option("DD_GAME_ARGS", "", 0, Type.TEXT),
    )

    fun find(name: String): Option? = all.firstOrNull { it.name == name }

    fun toggle(value: String, choice: String): String {
        val selected = value.split(',').filter { it.isNotEmpty() }.toMutableSet()
        if (!selected.remove(choice)) selected.add(choice)
        return selected.joinToString(",")
    }
}
