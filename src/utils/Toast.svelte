<script>
    import { fade, fly } from "svelte/transition";
    import { backOut } from "svelte/easing";
    import Toast from "../stores/toast";
</script>

<div class="toast-wrapper">
    {#if $Toast.show}
        <div class="toast-item {$Toast.type}"
            in:fly={{ delay: 0, duration: 300, x: 0, y: 50, opacity: 0.1, easing: backOut, }}
            out:fade={{ duration: 200, opacity: 0 }}>
            {$Toast.message}
        </div>
    {/if}
</div>

<style>
    .toast-wrapper {
        position: fixed;
        left: 0;
        right: 0;
        bottom: 14%;
        text-align: center;
        z-index: 9999;
    }
    .toast-item {
        gap: 5px;
        display: flex;
        align-items: center;
        border-radius: 10px;
        padding: 9px 15px;
        margin: 10px auto;
        width: fit-content;
        font-size: 16px;
        font-weight: 800;
        font-family: "Roboto Mono", monospace;
        color: var(--dark-color);
        border: 2px solid var(--dark-color);
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
    }

    
    :global(.danger) {
        background-color: var(--danger-color);
    }

    :global(.success) {
        background-color: var(--success-color);
    }
</style>
