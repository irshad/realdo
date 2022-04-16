<script>
    import { fade } from "svelte/transition";

    export let show = true;
</script>

{#if show}
    <div class="modal-overlay" transition:fade={{ duration: 150 }}>
        <div class="modal-container">
            <slot />
        </div>
    </div>
{/if}

<style>
    .modal-overlay {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(0, 0, 0, 0.5);
        z-index: 10;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }
    .modal-container {
        position: relative;
        padding: 24px;
        height: auto;
        margin: auto;
        width: 200px;
        border: 2px solid var(--dark-color);
        background-color: var(--light-color);
        border-radius: 16px;
    }
</style>
