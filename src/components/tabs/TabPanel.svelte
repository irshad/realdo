<script>
    export let value = 1;
	export let tab = 1;
</script>

{#if tab == value}
    <div class="tab-panel" id="tab-panel">
        <slot />
    </div>
{/if}

<style>
    .tab-panel {
        height: calc(100% - 40px);
        overflow-y: auto;
    }
</style>