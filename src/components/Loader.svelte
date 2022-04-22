<script>
    import { fade } from "svelte/transition";
    import { BarLoader } from 'svelte-loading-spinners';

    export let loading = true;
</script>

{#if loading}
    <div class="main-section" transition:fade={{ duration: 150 }}>
        <BarLoader size="60" color="#000" unit="px" duration="1s" />
    </div>
{/if}

<style>
    .main-section {
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 9999;
        inset: 0;
        position: absolute;
        background-color: var(--primary-color);
    }
</style>
